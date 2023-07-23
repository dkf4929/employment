package com.project.employment.service;

import com.project.employment.common.HttpSuplier;
import com.project.employment.dto.LoginRq;
import com.project.employment.entity.Member;
import com.project.employment.exception.LoginIdOrPasswordException;
import com.project.employment.jwt.JwtTokenProvider;
import com.project.employment.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public String login(LoginRq dto) {
        Member findMember = memberRepository.findByLoginId(dto.getLoginId()).orElseThrow(() -> {
            throw new LoginIdOrPasswordException();
        });

        if (!passwordEncoder.matches(dto.getPassword(), findMember.getPassword()))
            throw new LoginIdOrPasswordException();

        String token = tokenProvider.createToken(findMember.getLoginId(), List.of("ROLE_USER"));
        Cookie cookie = new Cookie("jwtToken", token);
        cookie.setMaxAge(30 * 60 * 1000);
        cookie.setPath("/");

        HttpSuplier.getResponse().addCookie(cookie);

        return "로그인 성공";
    }
}
