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
import lombok.extern.slf4j.Slf4j;

// to do : Security 적용 후 String memberId -> SecurityContextHolder

@Slf4j
@RequiredArgsConstructor
@Service
public class BudgetServiceImpl implements BudgetService {

    private final MemberRepository memberRepo;
    private final BudgetRepository budgetRepo;

    @Override
    public BudgetResponseDto suggestBudget(int totalAmount) {

        List<BudgetEntity> budgetEntityList = budgetRepo.findAll();

        Map<CategoryType, Double> proportionSumMap = calculateProportionSum(budgetEntityList);

        List<CategoryResponseDto> categoryRespDtoList = new ArrayList<>();
        
        for (CategoryType categoryType : proportionSumMap.keySet()) {
            double proportion = proportionSumMap.get(categoryType) / budgetEntityList.size();
            int amount = (int) (totalAmount * proportion);
            categoryRespDtoList.add(CategoryResponseDto.builder()
                    .categoryType(categoryType)
                    .amount(amount)
                    .proportion(proportion)
                    .build());
        }

        return BudgetResponseDto.builder()
                .totalAmount(totalAmount)
                .categoryRespDtoList(categoryRespDtoList)
                .build();
    }

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

        List<CategoryResponseDto> categoryResponseDtoList = configCategoryRespDtoList(categoryEntityList);

        return BudgetResponseDto.builder()
                .totalAmount(budgetEntity.getTotalAmount())
                .categoryRespDtoList(categoryResponseDtoList)
                .build();

    }

    private List<CategoryResponseDto> configCategoryRespDtoList(List<CategoryEntity> categoryEntityList) {

        List<CategoryResponseDto> categoryResponseDtoList = new ArrayList<CategoryResponseDto>();

        for (CategoryEntity categoryEntity : categoryEntityList) {
            categoryResponseDtoList.add(CategoryResponseDto.convertIntoDto(categoryEntity));
        }

        return categoryResponseDtoList;

    }

    @Override
    public void createBudget(BudgetRequestDto budgetReqDto) {

        String memberId = "iru";
        MemberEntity memberEntity = memberRepo.findByMemberId(memberId).get();

        int totalAmount = budgetReqDto.getTotalAmount();

        List<CategoryRequestDto> categoryReqDtoList = budgetReqDto.getCategoryReqDtoList();

        List<CategoryEntity> categoryEntityList = configCategoryEntityList(totalAmount, categoryReqDtoList);

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
        log.info("date : " + date);
        BudgetEntity budgetEntity = budgetRepo.findByMemberEntityAndCreatedDateBefore(memberEntity, date).get();

        int totalAmount = budgetReqDto.getTotalAmount();

        // to do : 새로 만드는 게 아닌 조회한 budgetEntity의 categoryEntityList를 수정하도록 수정
        List<CategoryRequestDto> categoryReqDtoList = budgetReqDto.getCategoryReqDtoList();

        List<CategoryEntity> categoryEntityList = configCategoryEntityList(totalAmount, categoryReqDtoList);

        budgetEntity.updateValue(totalAmount, categoryEntityList);

        budgetRepo.save(budgetEntity);
    }

    private List<CategoryEntity> configCategoryEntityList(int totalAmount, List<CategoryRequestDto> categoryReqDtoList) {
        
        List<CategoryEntity> categoryEntityList = new ArrayList<>();
        
        for (CategoryRequestDto categoryReqDto : categoryReqDtoList) {
            CategoryEntity categoryEntity = categoryReqDto.convertIntoEntity(categoryReqDto, totalAmount);
            categoryEntityList.add(categoryEntity);
        }
        
        return categoryEntityList;
    }
}
