package com.project.employment.handler;

import com.project.employment.member.Member;
import com.project.employment.member.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class GoogleLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
        Member loginMember = memberRepository.findByEmail(String.valueOf(principal.getAttributes().get("email"))).get();

        // 최초 구글 로그인 시 리다이렉트
        if (loginMember.getEditYn().equals("N")) {
            response.sendRedirect("/member/" + loginMember.getId());
        } else { // 구글 로그인 이력 있으면 홈 화면으로
            response.sendRedirect("/home");
        }
    }
}
