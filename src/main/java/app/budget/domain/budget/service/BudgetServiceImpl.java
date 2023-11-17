package app.budget.domain.budget.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import app.budget.domain.budget.dto.request.BudgetRequestDto;
import app.budget.domain.budget.dto.request.CategoryRequestDto;
import app.budget.domain.budget.dto.response.BudgetResponseDto;
import app.budget.domain.budget.dto.response.CategoryResponseDto;
import app.budget.domain.budget.entity.BudgetEntity;
import app.budget.domain.budget.entity.CategoryEntity;
import app.budget.domain.budget.entity.CategoryType;
import app.budget.domain.budget.repository.BudgetRepository;
import app.budget.domain.member.entity.MemberEntity;
import app.budget.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

// to do : Security 적용 후 String memberId -> SecurityContextHolder

@RequiredArgsConstructor
@Service
public class BudgetServiceImpl implements BudgetService {

    private final MemberRepository memberRepo;
    private final BudgetRepository budgetRepo;

    @Override
    public BudgetResponseDto suggestBudget(int totalAmount) {
        /*
         * to do : 현재는 DB에서 다 가져와서 비율 평균 연산을 수행하고 있다.
         * 이를 쿼리문으로 평균 연산을 해서 평균값을 가져오도록 바꾸면 더 효율적일 것 같다.
         */
        List<BudgetEntity> budgetEntityList = budgetRepo.findAll();

        // 전체 사용자의 카테고리별 비율합이 저장된 Map
        Map<CategoryType, Double> proportionSumMap = calculateProportionSum(budgetEntityList);

        List<CategoryResponseDto> categoryRespDtoList = new ArrayList<>();

        // 전체 사용자의 카테고리별 비율 평균과 그에 따른 제안 분배 금액 categoryRespDtoList에 저장
        for (CategoryType categoryType : proportionSumMap.keySet()) {
            double avgProportion = proportionSumMap.get(categoryType) / budgetEntityList.size();
            int suggestedAmount = (int) (totalAmount * avgProportion);
            categoryRespDtoList.add(CategoryResponseDto.builder()
                    .categoryType(categoryType)
                    .amount(suggestedAmount)
                    .proportion(avgProportion)
                    .build());
        }

        return BudgetResponseDto.builder()
                .totalAmount(totalAmount)
                .categoryRespDtoList(categoryRespDtoList)
                .build();
    }

    // 전체 사용자의 카테고리별 비율합을 Map에 저장 후 반환하는 메서드
    private Map<CategoryType, Double> calculateProportionSum(List<BudgetEntity> budgetEntityList) {

        Map<CategoryType, Double> proportionSumMap = new HashMap<>();

        for (BudgetEntity budgetEntity : budgetEntityList) {

            List<CategoryEntity> categoryEntityList = budgetEntity.getCategoryEntityList();

            for (CategoryEntity categoryEntity : categoryEntityList) {
                CategoryType categoryType = categoryEntity.getCategoryType();
                double proportion = categoryEntity.getProportion();
                proportionSumMap.computeIfPresent(categoryType, (key, value) -> proportionSumMap.get(key) + proportion);
                proportionSumMap.putIfAbsent(categoryType, proportion);
            }
        }

        return proportionSumMap;

    }

    @Override
    public BudgetResponseDto getBudget(String memberId) {

        MemberEntity memberEntity = memberRepo.findByMemberId(memberId).get();

        BudgetEntity budgetEntity = budgetRepo.findByMemberEntity(memberEntity).get();

        List<CategoryEntity> categoryEntityList = budgetEntity.getCategoryEntityList();

        List<CategoryResponseDto> categoryResponseDtoList = new ArrayList<CategoryResponseDto>();

        for (CategoryEntity categoryEntity : categoryEntityList) {
            categoryResponseDtoList.add(CategoryResponseDto.convertIntoDto(categoryEntity));
        }

        return BudgetResponseDto.builder()
                .totalAmount(budgetEntity.getTotalAmount())
                .categoryRespDtoList(categoryResponseDtoList)
                .build();

    }

    @Override
    public void createBudget(BudgetRequestDto budgetReqDto) {

        String memberId = "iru";
        MemberEntity memberEntity = memberRepo.findByMemberId(memberId).get();

        int totalAmount = budgetReqDto.getTotalAmount();

        List<CategoryRequestDto> categoryReqDtoList = budgetReqDto.getCategoryReqDtoList();

        List<CategoryEntity> categoryEntityList = new ArrayList<>();

        // categoryReqDto -> categoryEntity
        for (CategoryRequestDto categoryReqDto : categoryReqDtoList) {
            CategoryEntity categoryEntity = categoryReqDto.convertIntoEntity(categoryReqDto, totalAmount);
            categoryEntityList.add(categoryEntity);
        }

        budgetRepo.save(BudgetEntity.builder()
                .memberEntity(memberEntity)
                .categoryEntityList(categoryEntityList)
                .totalAmount(totalAmount)
                .build());

    }

    @Override
    public void updateBudget(BudgetRequestDto budgetReqDto) {
        String memberId = "iru";
        MemberEntity memberEntity = memberRepo.findByMemberId(memberId).get();

        // to do : 수정 시점 달의 예산 정보를 가져오도록 수정
        LocalDateTime date = LocalDateTime.now();
        BudgetEntity budgetEntity = budgetRepo.findByMemberEntityAndCreatedDateBefore(memberEntity, date).get();

        int totalAmount = budgetReqDto.getTotalAmount();

        List<CategoryRequestDto> categoryReqDtoList = budgetReqDto.getCategoryReqDtoList();

        /*
         * 클라이언트에서 넘어오는 category들의 순서가 보장돼 있지 않아서
         * CategoryType을 매핑시켜서 값을 수정해야 한다.
         * 반복적인 for문 사용으로 CateforyType을 찾지 않고 Map을 활용한다.
         */
        Map<CategoryType, Integer> updatedAmountMap = new HashMap<>();

        // client에서 넘어온 값 저장
        for (CategoryRequestDto categoryRequestDto : categoryReqDtoList) {
            updatedAmountMap.put(categoryRequestDto.getCategoryType(), categoryRequestDto.getAmount());
        }

        List<CategoryEntity> categoryEntityList = budgetEntity.getCategoryEntityList();

        // categoryEntity 값(비율, 금액) update
        for (CategoryEntity categoryEntity : categoryEntityList) {
            int updatedAmount = updatedAmountMap.get(categoryEntity.getCategoryType());
            double updatedProportion = (double) updatedAmount / totalAmount;

            categoryEntity.updateCategory(updatedAmount, updatedProportion);
        }

        budgetEntity.updateBudget(totalAmount, categoryEntityList);

        budgetRepo.save(budgetEntity);
    }

}
