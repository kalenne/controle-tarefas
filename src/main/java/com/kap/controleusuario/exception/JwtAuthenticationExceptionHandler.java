package com.kap.controleusuario.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.kap.controleusuario.utils.FormatLocalDateTime;

@RestControllerAdvice
public class JwtAuthenticationExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseException> handleJwtAuthenticationException(AuthenticationException ex) {
		FormatLocalDateTime fldt = new FormatLocalDateTime(LocalDateTime.now());

		ResponseException re = new ResponseException(fldt.formatDateTime(), ex.getMessage(),
				"Acesso Negado. Usuário não autenticado ou expirado.");

        return new ResponseEntity<>(re, HttpStatus.UNAUTHORIZED);
    }

}
