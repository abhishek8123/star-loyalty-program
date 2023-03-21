package com.cgs.loyalty.exception;

import java.util.List;

import com.cgs.loyalty.advice.ErrorDetails;

public class BadRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ErrorDetails> errors;

	public List<ErrorDetails> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorDetails> errors) {
		this.errors = errors;
	}

	public BadRequestException(List<ErrorDetails> errors) {

		this.errors = errors;
	}

}
