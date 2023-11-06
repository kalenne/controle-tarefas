package com.kap.controleusuario.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.kap.controleusuario.utils.FormatLocalDateTime;

@ControllerAdvice
public class HandlerException {
	
	@ExceptionHandler(NotFoundException.class)
	public final ResponseEntity<ResponseException> handlerNotFound(NotFoundException nfe, WebRequest request) {
		
		FormatLocalDateTime fldt = new FormatLocalDateTime(LocalDateTime.now());
		
		ResponseException re = new ResponseException(fldt.formatDateTime(), nfe.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<ResponseException>(re, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(AlreadyExistsException.class)
	public final ResponseEntity<ResponseException> handlerAlreadyExists(AlreadyExistsException aee, WebRequest request){
		FormatLocalDateTime fldt = new FormatLocalDateTime(LocalDateTime.now());
		
		ResponseException re = new ResponseException(fldt.formatDateTime(), aee.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<ResponseException>(re, HttpStatus.CONFLICT);
		
	}

}
