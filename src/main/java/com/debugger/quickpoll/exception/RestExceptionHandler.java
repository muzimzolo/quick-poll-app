package com.debugger.quickpoll.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.debugger.quickpoll.error.ErrorDetails;
import com.debugger.quickpoll.error.ValidationError;



@ControllerAdvice
public class RestExceptionHandler {

	@Inject
	private MessageSource messageSource;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorDetails handleValidationError(MethodArgumentNotValidException manve,
			HttpServletRequest request) {
		ErrorDetails errorDetail = new ErrorDetails();
		// Populate errorDetail instance
		errorDetail.setTimeStamp(new Date().getTime());
		errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
		String requestPath = (String) request.getAttribute("javax.servlet.error.request_uri");
		if (requestPath == null) {
			requestPath = request.getRequestURI();
		}
		
		errorDetail.setTitle("Validation Failed");
		errorDetail.setDetail("Input validation failed");
		errorDetail.setDeveloperMessage(manve.getClass().getName());
		
		// Create ValidationError instances
		List<FieldError> fieldErrors = manve.getBindingResult().getFieldErrors();
		for (FieldError fe : fieldErrors) {
			List<ValidationError> validationErrorList = errorDetail.getErrors().get(fe.getField());
			if (validationErrorList == null) {
				validationErrorList = new ArrayList<ValidationError>();
				errorDetail.getErrors().put(fe.getField(), validationErrorList);
			}
			ValidationError validationError = new ValidationError();
			validationError.setCode(fe.getCode());
			validationError.setMessage(messageSource.getMessage(fe, null));
			validationErrorList.add(validationError);
		}
		return errorDetail;
	}
}