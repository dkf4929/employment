package com.project.employment.oauth2;

import com.project.employment.common.HttpSuplier;
import com.project.employment.entity.Member;
import com.project.employment.jwt.JwtTokenProvider;
import com.project.employment.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;
    private final HttpSession httpSession;
    private final JwtTokenProvider tokenProvider;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oauth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oauth2User.getAttributes());

        attributes.getAttributes().entrySet().forEach(stringObjectEntry -> System.out.println("stringObjectEntry = " + stringObjectEntry));

        // user 테이블에 구글 메일 계정 존재 -> 토큰 발급
        // user 테이블에 구글 메일 계정 존재하지 않을 경우 user테이블에 저장 후 토큰 발급
        Member member = saveOrUpdate(attributes);
        System.out.println("member = " + member);
        Cookie cookie = new Cookie("jwtToken", tokenProvider.createToken(attributes.getEmail(), List.of("ROLE_USER")));
        cookie.setMaxAge(30 * 60 * 1000);
        cookie.setPath("/");

        HttpSuplier.getResponse().addCookie(cookie); //response 객체 직접 가져오기

        httpSession.setAttribute("user", new SessionUser(member));

        return new DefaultOAuth2User(member.getAuthorities(), attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    private Member saveOrUpdate(OAuthAttributes attributes){
        Optional<Member> member = memberRepository.findByEmail(attributes.getEmail());

        if (member.isEmpty()) {
            return memberRepository.save(attributes.toEntity());
        }

        return member.get();
    }
}
