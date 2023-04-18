package com.project.employment.repository;

import com.project.employment.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public Optional<Member> findByLoginId(String loginId);

    public int countByLoginId(String loginId);

    public Optional<Member> findByEmail(String email);
}
