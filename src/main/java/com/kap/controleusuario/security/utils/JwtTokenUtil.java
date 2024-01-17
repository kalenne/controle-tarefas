package com.kap.controleusuario.security.utils;

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
	
	static final String CLAIM_KEY_USERNAME = "sub";
	static final String CLAIM_KEY_ROLE = "role";
	static final String CLAIM_KEY_CREATED = "created";
	
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
	
	public String getAuthority(String token) {
		String role;
		try {
			Claims claims = getClaimsFromToken(token);
			role = (String) claims.get("role");
		} catch (Exception e) {
			return null;
		}
		return role;
	}
	
	public Date getExpirationDateFromToken(String token) {
		Date expiracao;
		try {
			Claims claims = getClaimsFromToken(token);
			expiracao = claims.getExpiration();
		} catch (Exception e ) {
			expiracao = null;
		}
		
		return expiracao;
	}
	
	public String refreshToken (String token) {
		String tokenAtualizado;
		try {
			Claims claims = getClaimsFromToken(token);
			claims.put(CLAIM_KEY_CREATED, new Date());
			tokenAtualizado = gerarToken(claims);
		}catch (Exception e ) {
			tokenAtualizado = null;
		}
		return tokenAtualizado;
	}
	
	public boolean tokenValido(String token) {
		return !tokenExpirado(token);
	}
	
	public String obterToken (UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		userDetails.getAuthorities()
		.forEach(authority -> {
			claims.put(CLAIM_KEY_ROLE, authority.getAuthority());
			claims.put(CLAIM_KEY_CREATED, new Date());
		});
		
		return gerarToken(claims);
	}
	
	private Claims getClaimsFromToken(String token) {
		Claims claims;
		
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e ) {
			claims = null;
		}
		return claims;
		
	}
	
	private String gerarToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(gerarDataExpiracao())
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
	private Date gerarDataExpiracao() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}
		
	private boolean tokenExpirado(String token) {
		Date dataExpiracao = this.getExpirationDateFromToken(token);
		
		if(dataExpiracao == null) {
			return false;
		}
		
		return dataExpiracao.before(new Date());
	}	
}
