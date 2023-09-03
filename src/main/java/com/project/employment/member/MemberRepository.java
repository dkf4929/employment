package com.project.employment.member;

import com.project.employment.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public Optional<Member> findByLoginId(String loginId);

    public int countByLoginId(String loginId);

    public Optional<Member> findByEmail(String email);
}
