package com.hungerbox.wallet_service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hungerbox.wallet_service.dto.response.ApiResponse;

import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
		StringBuilder errors = new StringBuilder();
		ex.getBindingResult().getFieldErrors().forEach(
				error -> errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; "));

		ApiResponse response = new ApiResponse(false, "Validation Failed", errors.toString());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException ex) {
		logger.error("Runtime Exception: {}", ex.getMessage());
		ApiResponse response = new ApiResponse(false, "An error occurred", ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(feign.FeignException.class)
	public ResponseEntity<ApiResponse> handleFeignStatusException(feign.FeignException e) {
	    logger.error("Feign Client Error: Status {}, Message: {}", e.status(), e.contentUTF8());
	    // Extract the message from the other service if possible
	    String message = "Service Communication Error: " + (e.contentUTF8().isEmpty() ? e.getMessage() : e.contentUTF8());
	    return new ResponseEntity<>(new ApiResponse(false, "Remote Service Failure", message), HttpStatus.BAD_GATEWAY);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse> handleGeneralException(Exception ex) {
		logger.error("Internal Server Error: ", ex);
		ApiResponse response = new ApiResponse(false, "Internal Server Error", "Contact Admin");
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}