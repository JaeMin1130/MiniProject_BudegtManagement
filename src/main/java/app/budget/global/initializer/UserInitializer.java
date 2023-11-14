package app.budget.global.initializer;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import app.budget.domain.budget.entity.Budget;
import app.budget.domain.user.entity.AgeGroup;
import app.budget.domain.user.entity.User;
import app.budget.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UserInitializer implements ApplicationRunner {

    private final UserRepository userRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Budget budget = new Budget();
        // userRepo.save(User.builder().ageGroup(AgeGroup.getAgeGroup("10대")).build());
    }
}
