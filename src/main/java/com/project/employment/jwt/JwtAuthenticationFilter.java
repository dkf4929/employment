package com.project.employment.jwt;

import com.project.employment.enums.AllPermitPath;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // jwt 인증 필터를 거치지 않을 경로 설정.
        AntPathMatcher pathMatcher = new AntPathMatcher();

        return Arrays.stream(AllPermitPath.values())
                .anyMatch(path -> pathMatcher.match(path.getPath(), request.getRequestURI()) || path.getPath().equals(request.getRequestURI()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .forEach((s) -> log.info("authority", s.getAuthority()));
        }

        filterChain.doFilter(request, response);
    }
}
