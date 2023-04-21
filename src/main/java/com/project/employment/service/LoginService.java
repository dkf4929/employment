package com.project.employment.service;

import com.project.employment.common.HttpSuplier;
import com.project.employment.dto.LoginDto;
import com.project.employment.entity.Member;
import com.project.employment.exception.NoSuchUserException;
import com.project.employment.exception.PasswordMismatchException;
import com.project.employment.jwt.JwtTokenProvider;
import com.project.employment.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public String login(LoginDto dto) {
        Member findMember = memberRepository.findByLoginId(dto.getLoginId()).orElseThrow(() -> {
            throw new NoSuchUserException("등록된 사용자가 아닙니다.");
        });

        if (!passwordEncoder.matches(dto.getPassword(), findMember.getPassword()))
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");

        String token = tokenProvider.createToken(findMember.getLoginId(), List.of("ROLE_USER"));
        Cookie cookie = new Cookie("jwtToken", token);
        cookie.setMaxAge(30 * 60 * 1000);
        cookie.setPath("/");

        HttpSuplier.getResponse().addCookie(cookie);

        return "로그인 성공";
    }
}
