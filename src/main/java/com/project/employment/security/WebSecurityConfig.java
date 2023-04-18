package com.project.employment.security;

import com.project.employment.jwt.JwtAuthenticationFilter;
import com.project.employment.jwt.JwtTokenProvider;
import com.project.employment.oauth2.CustomOAuth2UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
//@EnableWebSecurity
@Slf4j
public class WebSecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
//    private final GoogleLoginSuccessHandler googleLoginSuccessHandler;
    private String[] userPath = new String[]{};
    private String[] whiteList = new String[]{"/member/**", "/resources/**", "/"};
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
//        http.httpBasic().disable()
//                .authorizeHttpRequests()
//                .requestMatchers(whiteList).permitAll()
//                .requestMatchers(userPath).hasRole("ROLE_USER")
//                .anyRequest().authenticated()
//                .and()
//                    .logout()
//                    .logoutSuccessUrl("/")
//                .and()
//                .exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
//                    @Override
//                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//                        System.out.println("URI --> " + request.getRequestURI());
//                    }
//                })
//                .authenticationEntryPoint(new AuthenticationEntryPoint() {
//                    @Override
//                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//                        System.out.println("URI --> " + request.getRequestURI());
//                    }
//                })
//                .and()
////                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
//                .oauth2Login()
//                .userInfoEndpoint()
//                .userService(customOAuth2UserService);
////                .and()
////                .successHandler(googleLoginSuccessHandler);
//
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

}
