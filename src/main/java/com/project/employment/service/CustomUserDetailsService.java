package com.project.employment.service;

import com.project.employment.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String pk) throws UsernameNotFoundException {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

        if (pk.matches(regex)) { // 구글 로그인
            return memberRepository.findByEmail(pk).orElseThrow(() -> new IllegalStateException("등록된 사용자가 아닙니다."));
        } else { // 아이디 패스워드 로그인
            return memberRepository.findByLoginId(pk).orElseThrow(() -> new IllegalStateException("등록된 사용자가 아닙니다."));
        }
    }
}
