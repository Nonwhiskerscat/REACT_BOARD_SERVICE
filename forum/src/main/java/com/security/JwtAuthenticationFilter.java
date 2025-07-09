package com.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component // 또는 SecurityConfig에서 @Bean으로 등록
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private TokenProvider tokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String token = tokenProvider.resolveToken(request);
			if (token != null && tokenProvider.validateJwt(token)) {
				Authentication auth = tokenProvider.getAuthentication(token);

				// details 주입 (UsernamePasswordAuthenticationToken 인 경우에만)
				if (auth instanceof org.springframework.security.authentication.AbstractAuthenticationToken aat) {
					aat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				}

				SecurityContextHolder.getContext().setAuthentication(auth);
			}
			filterChain.doFilter(request, response);
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 로그인·검증 엔드포인트는 필터 패스
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String p = request.getServletPath();
		return p.startsWith("/api/auth/login") || p.startsWith("/api/auth/validate");
	}
}
