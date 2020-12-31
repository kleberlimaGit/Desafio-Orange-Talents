package com.desafio.orange.talents.controllers.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.desafio.orange.talents.services.exceptions.DatabaseException;
import com.desafio.orange.talents.services.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		StandardError standardError = new StandardError();
		standardError.setTimestamp(Instant.now());
		standardError.setStatus(status.value());
		standardError.setError("Página não encontrada.");
		standardError.setMessage(e.getMessage());
		standardError.setPath(request.getRequestURI());
		
		return ResponseEntity.status(status).body(standardError);
		
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request){
		
		HttpStatus status = HttpStatus.CONFLICT;
		
		StandardError standardError = new StandardError();
		standardError.setTimestamp(Instant.now());
		standardError.setStatus(status.value());
		standardError.setError("Database exception.");
		standardError.setMessage(e.getMessage());
		standardError.setPath(request.getRequestURI());
		
		return ResponseEntity.status(status).body(standardError);
	}
		
		@ExceptionHandler(InvalidFormatException.class)
		public ResponseEntity<StandardError> invalidFormat(InvalidFormatException e, HttpServletRequest request){
			
			HttpStatus status = HttpStatus.BAD_REQUEST;
			
			StandardError standardError = new StandardError();
			standardError.setTimestamp(Instant.now());
			standardError.setStatus(status.value());
			standardError.setError("Wrong date format");
			standardError.setMessage("Por favor digite a data no formato YYYY-MM-DD");
			standardError.setPath(request.getRequestURI());
			
			return ResponseEntity.status(status).body(standardError);
			
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> argumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request){
		
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		
		ValidationError standardError = new ValidationError();
		standardError.setTimestamp(Instant.now());
		standardError.setStatus(status.value());
		standardError.setError("Validation exception.");
		standardError.setMessage("Error de validação");
		standardError.setPath(request.getRequestURI());
		
		for(FieldError f : e.getBindingResult().getFieldErrors()) {
			
			standardError.addError(f.getField(), f.getDefaultMessage());
		}
		
		return ResponseEntity.status(status).body(standardError);
		
	}
}
