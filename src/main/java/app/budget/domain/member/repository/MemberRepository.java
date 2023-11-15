package app.budget.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.budget.domain.member.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {
    public Optional<MemberEntity> findByMemberId(String memberId);
}
