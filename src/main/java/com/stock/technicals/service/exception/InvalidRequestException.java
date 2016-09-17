package com.stock.technicals.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author Shawn Stewart
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Request")
public class InvalidRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String errorReason;

	public InvalidRequestException() {
	}

	public InvalidRequestException(String errorReason) {
		this.errorReason = errorReason;
	}

	public String getErrorReason() {
		return errorReason;
	}

	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}
}
