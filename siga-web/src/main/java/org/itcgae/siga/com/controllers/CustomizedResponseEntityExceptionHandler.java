package org.itcgae.siga.com.controllers;

import org.apache.log4j.Logger;
import org.itcgae.siga.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger LOGGER = Logger.getLogger(CustomizedResponseEntityExceptionHandler.class);
	
	@ExceptionHandler(BusinessException.class)
	public final ResponseEntity<String> handleUserBussinessException(BusinessException ex, WebRequest request) {
		LOGGER.error("ExceptionHandler BusinessException :: ", ex);
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<String> handleAllExceptions(Exception ex, WebRequest request) {
		LOGGER.error("ExceptionHandler Exception :: " + ex.getMessage(), ex);
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
