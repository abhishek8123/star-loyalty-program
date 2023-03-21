package com.cgs.loyalty.advice;

public class ErrorDetails {

	private String target;
	private String message;
	
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ErrorDetails(String target, String message) {
		super();
		this.target = target;
		this.message = message;
	}
}

