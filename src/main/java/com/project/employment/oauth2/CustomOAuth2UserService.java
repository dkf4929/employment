package com.project.employment.oauth2;

import com.project.employment.jwt.JwtTokenProvider;
import com.project.employment.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
@Getter
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

        // user 테이블에 구글 메일 계정 존재 -> 토큰 발급
        // user 테이블에 구글 메일 계정 존재하지 않을 경우 user테이블에 저장 후 토큰 발급
//        Members member = saveOrUpdate(attributes);
//        Cookie cookie = new Cookie("jwtToken", tokenProvider.createToken(attributes.getName(), List.of("ROLE_USER")));
//        cookie.setMaxAge(30 * 60 * 1000);
//        cookie.setPath("/");
//
//        HttpSupplier.getResponse().addCookie(cookie); //response 객체 직접 가져오기
//
//        httpSession.setAttribute("user", new SessionUser(user));
//
//        return new DefaultOAuth2User(user.getAuthorities(), attributes.getAttributes(), attributes.getNameAttributeKey());

        return null;
    }

//    private Users saveOrUpdate(OAuthAttributes attributes){
//        Users user = userRepository.findByEmail(attributes.getEmail()).map(entity->entity.update(attributes.getName(), attributes.getEmail()))
//                .orElse(attributes.toEntity());
//
//        return userRepository.save(user);
//    }
}
