package com.security;

import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service

public class TokenProvider {
	@Autowired
	private UserDetailsService userDetailsService;

	private static final String SECRET = "XaR3uPf9WmZqYkBnCtDsFrJvEgHiLmNoPqStVuWxYzAbCdEfGhIjKlMnOpQrStUv";
	private static final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)); // 64+ bytes 보장

	/* 1. 토큰 생성 */
	public String createJwt(String code) throws Exception {
		Date exprTime = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
		return Jwts.builder().signWith(SignatureAlgorithm.HS512, SECRET_KEY).setSubject(code)
				.setIssuedAt(new Date()).setExpiration(exprTime).compact();
	}

	/* 2. 토큰 유효성 검사 */
	public Boolean validateJwt(String token) throws Exception {
		try {
			Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
			return true;
		}

		catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	/* 3. 요청 헤더에서 토큰 추출 */
	public String resolveToken(HttpServletRequest request) throws Exception {
		String bearer = request.getHeader("Authorization");
		if (bearer != null && bearer.startsWith("Bearer "))
			return bearer.substring(7);
		return null;
	}

	/* 4. 토큰에서 이메일 추출 */
	public String getCode(String token) throws Exception {
		return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody().getSubject();
	}

	/* 5. Authentication 객체 반환 */
	public Authentication getAuthentication(String token) throws Exception {
		String email = this.getCode(token);
		return new UsernamePasswordAuthenticationToken(email, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
	}

}