package com.kap.controleusuario.security.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kap.controleusuario.entities.Usuario;
import com.kap.controleusuario.security.JwtUsuario;

public class JWTAutenticarFilter extends UsernamePasswordAuthenticationFilter {
	
	@Value("${jwt.expiration}")
	private int TOKEN_EXPIRACAO;
	
	private String TOKEN_SENHA = "4a9c7b20-872e-11ee-b9d1-0242ac120002";
	
	private final AuthenticationManager authenticationManager;

	public JWTAutenticarFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws RuntimeException {
		
		try {
			Usuario usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					usuario.getEmail(),
					usuario.getSenha(),
					new ArrayList<>()
					));
		} catch (IOException e) {
			throw new RuntimeException("Falha ao autenticar Usuario");
		}			
	}
	
	@Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
		
		JwtUsuario usuarioData = (JwtUsuario) authResult.getPrincipal();
		
		String token = JWT.create().withSubject(usuarioData.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRACAO ))
				.sign(Algorithm.HMAC512(TOKEN_SENHA));
		
		response.addHeader("Access-Control-Allow-Origin", "**");
		response.addHeader("Authorization", JWTValidarFilter.ATRIBUTO_PREFIXO + token);
		response.getWriter().write(token);
		response.getWriter().flush();
	}
}
