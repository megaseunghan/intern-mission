package com.mission.intern.global.filter;

import com.mission.intern.global.jwt.JwtUtil;
import com.mission.intern.global.security.CustomUserDetailService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class AuthenticateFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = resolveToken(request);

        if (jwtUtil.validateToken(accessToken)) {
            Claims claims = jwtUtil.getClaims(accessToken);
            String username = claims.getSubject();
            Set<SimpleGrantedAuthority> authorities = jwtUtil.getAuthorities(claims);
            UserDetails principal = userDetailService.loadUserByUsername(username);
            Authentication authentication = new UsernamePasswordAuthenticationToken(principal, "", authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private String resolveToken(HttpServletRequest request) {
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            return accessToken.substring(7);
        }
        return null;
    }
}
