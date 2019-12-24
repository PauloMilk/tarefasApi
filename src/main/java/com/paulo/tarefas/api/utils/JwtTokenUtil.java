package com.paulo.tarefas.api.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

	static final String CLAY_KEY_USERNAME = "sub";
	static final String CLAY_KEY_ROLE = "role";
	static final String CLAY_KEY_CREATED = "created";

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	public String getUsernameFromToken(String token) {
		String username;
		try {
			Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	public Date getDateExpirationFromToken(String token) {
		Date expiration;
		try {
			Claims claims = getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}

	public String refreshToken(String token) {
		String refreshedToken;
		try {
			Claims claims = getClaimsFromToken(token);
			claims.put(CLAY_KEY_CREATED, new Date());
			refreshedToken = gerarToken(claims);
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}

	public boolean tokenValid(String token) {
		return !tokenExpirado(token);
	}

	public String getToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAY_KEY_USERNAME, userDetails.getUsername());
		userDetails.getAuthorities().forEach(auth -> claims.put(CLAY_KEY_ROLE, auth.getAuthority()));
		claims.put(CLAY_KEY_CREATED, new Date());
		return gerarToken(claims);
	}

	private boolean tokenExpirado(String token) {
		Date dataExpricao = this.getDateExpirationFromToken(token);
		if (dataExpricao == null) {
			return false;
		}
		return dataExpricao.before(new Date());
	}

	private String gerarToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(gerarDataExpiracao())
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	private Date gerarDataExpiracao() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}

}
