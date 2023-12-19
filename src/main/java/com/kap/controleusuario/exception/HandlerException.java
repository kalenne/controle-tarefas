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
	
	private FormatLocalDateTime fldt;

	@ExceptionHandler(NotFoundException.class)
	public final ResponseEntity<ResponseException> handlerNotFound(NotFoundException nfe, WebRequest request) {

		ResponseException re = new ResponseException(fldt.dbToUser(LocalDateTime.now()), nfe.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(re, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(AlreadyExistsException.class)
	public final ResponseEntity<ResponseException> handlerAlreadyExists(AlreadyExistsException aee,
			WebRequest request) {
		
		ResponseException re = new ResponseException(fldt.dbToUser(LocalDateTime.now()), aee.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(re, HttpStatus.CONFLICT);

	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public final ResponseEntity<ResponseException> handlerBadRequest(MethodArgumentNotValidException manv, WebRequest request) {
		
		List<String> errors = manv.getBindingResult().getFieldErrors()
	            .stream()
	            .map(DefaultMessageSourceResolvable::getDefaultMessage)
	            .collect(Collectors.toList());

		ResponseException re = new ResponseException(fldt.dbToUser(LocalDateTime.now()), errors.toString(),
				request.getDescription(false));

		return new ResponseEntity<>(re, HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public final ResponseEntity<ResponseException> handlerUnauthorized(UnauthorizedException ue,
			WebRequest request) {

		ResponseException re = new ResponseException(fldt.dbToUser(LocalDateTime.now()), ue.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(re, HttpStatus.UNAUTHORIZED);

	}
	
	@ExceptionHandler(BadRequestException.class)
	public final ResponseEntity<ResponseException> handlerIllegalArgument(BadRequestException bre,
			WebRequest request) {

		ResponseException re = new ResponseException(fldt.dbToUser(LocalDateTime.now()), bre.getMessage(),
				request.getDescription(false));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re);

	}
	
	
	
}
