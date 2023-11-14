package app.budget.global.initializer;

import java.util.Date;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import app.budget.domain.budget.entity.Budget;
import app.budget.domain.budget.repository.BudgetRepository;
import app.budget.domain.user.entity.User;
import app.budget.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class BudgetInitializer implements ApplicationRunner {

    private final BudgetRepository budgetRepo;
    private final UserRepository userRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // User user = userRepo.save(User.builder().build());
        // Budget budget = budgetRepo.save(Budget.builder().user(user).build());
        // user.setBudget(budget);
        // userRepo.save(user);

        // System.out.println("저장 완료");
        // userRepo.delete(user);
    }
}
