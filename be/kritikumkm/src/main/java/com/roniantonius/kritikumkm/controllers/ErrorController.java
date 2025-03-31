package com.roniantonius.kritikumkm.controllers;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.roniantonius.kritikumkm.domain.dtos.ErrorDto;
import com.roniantonius.kritikumkm.exceptions.BaseException;
import com.roniantonius.kritikumkm.exceptions.StorageException;

import lombok.extern.slf4j.Slf4j;

@RestController
@ControllerAdvice
@Slf4j
public class ErrorController {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		log.error("Caught Method Argument Not Valid Exception");
		String pesanError = ex
				.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(eror -> eror.getField() + ": " + eror.getDefaultMessage())
				.collect(Collectors.joining(", "));
		
		ErrorDto errorDto = ErrorDto.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.message(pesanError)
				.build();
		
		return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(StorageException.class)
	public ResponseEntity<ErrorDto> handleStorageException(StorageException ex){
		log.error("CAught Storage exception error", ex);
		
		ErrorDto errorDto = ErrorDto.builder()
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.message("Unable to save or retrieve resources/files at this time")
				.build();
		
		return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// urutan dari Exception > Base > Storage 
	@ExceptionHandler(BaseException.class)
	public ResponseEntity<ErrorDto> handleBaseException(BaseException ex){
		log.error("CAught Base exception error", ex);
		
		ErrorDto errorDto = ErrorDto.builder()
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.message("An unexpected base exception error occur")
				.build();
		
		return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDto> handleException(Exception ex){
		log.error("CAught unexpected exception error", ex);
		
		ErrorDto errorDto = ErrorDto.builder()
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.message("An unexpected exception error occur")
				.build();
		
		return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
