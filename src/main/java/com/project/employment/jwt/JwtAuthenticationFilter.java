package com.project.employment.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;
    private String[] whiteList = new String[]{"/api/login", "/static/js/**", "/static/css/**", "/index.html", "/api/signup"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String URI = ((HttpServletRequest) request).getRequestURI();

        if (!Arrays.stream(whiteList).anyMatch(a -> URI.contains(a)) && !URI.equals("/")) {
            String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                        .forEach((s) -> log.info("authority", s.getAuthority()));
            }
        }
        chain.doFilter(request, response);
    }
}
