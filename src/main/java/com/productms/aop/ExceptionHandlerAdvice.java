package com.productms.aop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.productms.exception.ProductException;

/***
 * ExceptionHandlerAdvice : Class
 * 
 * Controller Advice to handle Exceptions
 * 
 */
@ControllerAdvice
@Component
public class ExceptionHandlerAdvice {
	
	@ExceptionHandler({ProductException.class})
	public ResponseEntity<String> productException(ProductException ex){
		return new ResponseEntity<>(ex.getMessage()!=""?ex.getMessage():"Product by specified ID not found!", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({Exception.class})
	public ResponseEntity<String> loginException(){
		return new ResponseEntity<>("Please log in with valid credentials first!", HttpStatus.UNAUTHORIZED);
	}
}
