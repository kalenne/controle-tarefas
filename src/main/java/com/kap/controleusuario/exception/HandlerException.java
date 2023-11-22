package com.kap.controleusuario.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.kap.controleusuario.utils.FormatLocalDateTime;

@ControllerAdvice
public class HandlerException {

	@ExceptionHandler(NotFoundException.class)
	public final ResponseEntity<ResponseException> handlerNotFound(NotFoundException nfe, WebRequest request) {

		FormatLocalDateTime fldt = new FormatLocalDateTime(LocalDateTime.now());

		ResponseException re = new ResponseException(fldt.formatDateTime(), nfe.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<ResponseException>(re, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(AlreadyExistsException.class)
	public final ResponseEntity<ResponseException> handlerAlreadyExists(AlreadyExistsException aee,
			WebRequest request) {
		FormatLocalDateTime fldt = new FormatLocalDateTime(LocalDateTime.now());

		ResponseException re = new ResponseException(fldt.formatDateTime(), aee.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<ResponseException>(re, HttpStatus.CONFLICT);

	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public final ResponseEntity<ResponseException> handlerBadRequest(MethodArgumentNotValidException manv, WebRequest request) {

		FormatLocalDateTime fldt = new FormatLocalDateTime(LocalDateTime.now());
		
		List<String> errors = manv.getBindingResult().getFieldErrors()
	            .stream()
	            .map(DefaultMessageSourceResolvable::getDefaultMessage)
	            .collect(Collectors.toList());

		ResponseException re = new ResponseException(fldt.formatDateTime(), errors.toString(),
				request.getDescription(false));

		return new ResponseEntity<ResponseException>(re, HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public final ResponseEntity<ResponseException> handlerUnauthorized (UnauthorizedException ue,
			WebRequest request) {
		FormatLocalDateTime fldt = new FormatLocalDateTime(LocalDateTime.now());

		ResponseException re = new ResponseException(fldt.formatDateTime(), ue.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<ResponseException>(re, HttpStatus.UNAUTHORIZED);

	}

}
