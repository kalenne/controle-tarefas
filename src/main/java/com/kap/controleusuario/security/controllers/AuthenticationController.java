package com.kap.controleusuario.security.controllers;

import java.util.Optional;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kap.controleusuario.response.Response;
import com.kap.controleusuario.security.dto.JwtAuthenticationDto;
import com.kap.controleusuario.security.dto.TokenDto;
import com.kap.controleusuario.security.utils.JwtTokenUtil;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {
	
	private static final String TOKEN_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@PostMapping
	public ResponseEntity<Response<TokenDto>> gerarTokenJwt
		(@Valid @RequestBody JwtAuthenticationDto authenticationDto, BindingResult result) throws AuthenticationException {
		
		Response<TokenDto> response = new Response<TokenDto>();
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationDto.getEmail(), authenticationDto.getSenha()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDto.getEmail());
		
		String token = jwtTokenUtil.obterToken(userDetails);
		
		response.setData(new TokenDto(token));
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<Response<TokenDto>> gerarRefreshToken(HttpServletRequest request) {
		Response<TokenDto> response = new Response<TokenDto>();
		
		Optional<String> token = Optional.ofNullable(request.getHeader(TOKEN_HEADER));
		
		if (token.isPresent() && token.get().startsWith(BEARER_PREFIX)) {
			token = Optional.of(token.get().substring(7));
		}
		
		if(!token.isPresent()) {
			response.getErrors().add("Token não informado.");
		} else if (!jwtTokenUtil.tokenValido(token.get())) {
			response.getErrors().add("Token inválido ou expirado.");
		}
		
		if(!response.getErrors().isEmpty()) {
			return ResponseEntity.badRequest().body(response);
		}
		
		String tokenAtualizado = jwtTokenUtil.refreshToken(token.get());
		
		response.setData (new TokenDto(tokenAtualizado));
		return ResponseEntity.ok(response);
	}
	

}
