package org.itcgae.siga.com.controllers;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.gen.ErrorDetail;
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
	public final ResponseEntity<ErrorDetail> handleUserBussinessException(BusinessException ex, WebRequest request) {
		LOGGER.error("ExceptionHandler BusinessException :: ", ex);
		ErrorDetail error = new ErrorDetail();
		error.setMessage(ex.getMessage());
		error.setDescription(printStackTraceToString(ex));
		return new ResponseEntity<ErrorDetail>(error, HttpStatus.PRECONDITION_REQUIRED);
	}

	private String printStackTraceToString(Exception e) {
		String retorno = null;
		if (e != null) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			retorno = errors.toString();
		}
		return retorno;
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetail> handleAllExceptions(Exception ex, WebRequest request) {
		LOGGER.error("ExceptionHandler Exception :: " + ex.getMessage(), ex);
		ErrorDetail error = new ErrorDetail();
		error.setMessage("Error interno de la aplicación");
		error.setDescription(printStackTraceToString(ex));
		return new ResponseEntity<ErrorDetail>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
