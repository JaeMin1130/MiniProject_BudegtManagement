package app.budget.global.initializer;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import app.budget.domain.budget.entity.BudgetEntity;
import app.budget.domain.member.entity.AgeGroup;
import app.budget.domain.member.entity.MemberEntity;
import app.budget.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MemberInitializer implements ApplicationRunner {

    private final MemberRepository userRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userRepo.save(MemberEntity.builder()
                .memberId("iru")
                .password("1234")
                .ageGroup(AgeGroup.getAgeGroup("20대"))
                .build());
    }
}
