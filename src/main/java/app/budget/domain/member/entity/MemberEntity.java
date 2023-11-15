package app.budget.domain.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "member")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String memberId;
    private String password;

    @Enumerated(EnumType.STRING)
    private AgeGroup ageGroup;

    @Builder
    public MemberEntity(String memberId, String password, AgeGroup ageGroup){
        this.memberId = memberId;
        this.password = password;
        this.ageGroup = ageGroup;
    }
}
