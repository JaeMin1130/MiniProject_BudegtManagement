// package app.budget.service;

// import java.util.ArrayList;
// import java.util.List;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.test.context.junit.jupiter.SpringExtension;

// import app.budget.domain.budget.dto.request.BudgetRequestDto;
// import app.budget.domain.budget.dto.request.CategoryRequestDto;
// import app.budget.domain.budget.entity.CategoryType;
// import app.budget.domain.budget.repository.BudgetRepository;
// import app.budget.domain.budget.service.BudgetService;
// import app.budget.domain.budget.service.BudgetServiceImpl;
// import app.budget.domain.member.repository.MemberRepository;

// @ExtendWith(SpringExtension.class)
// public class BudgetServiceTest {

//     // Test 주체
//     BudgetService budgetService;

//     // Test 협력자
//     @MockBean
//     BudgetRepository budgetRepo;

//     @MockBean
//     MemberRepository memberRepo;

//     // Test를 실행하기 전마다 BudgetService에 가짜 객체를 주입시켜준다. 
//     @BeforeEach
//     void setUp(){
//         budgetService = new BudgetServiceImpl(budgetRepo, memberRepo);
//     }

//     @Test
//     void createBudgetTest() {
//         List<CategoryRequestDto> categoryReqDtoList = new ArrayList<>();
        
//         int totalAmount = 0;
//         for (CategoryType categoryType : CategoryType.values()) {
//             int randomAmount = ((int) Math.random() * 5 + 1) * 100000; // 10만 ~ 50만
//             totalAmount += randomAmount;
//             categoryReqDtoList.add(CategoryRequestDto.builder()
//                     .categoryType(categoryType)
//                     .amount(randomAmount)
//                     .build());
//         }

//         BudgetRequestDto budgetRequestDto = BudgetRequestDto.builder()
//                 .totalAmount(totalAmount)
//                 .categoryReqDtoList(categoryReqDtoList)
//                 .build();

//         budgetService.createBudget(budgetRequestDto);
//     }
// }
