package com.cgs.loyalty.advice;

public class APIResponce {

	private Integer status;
	private Object errors;

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Object getErrors() {
		return errors;
	}
	public void setErrors(Object errors) {
		this.errors = errors;
	}
	public APIResponce() {
		super();
		this.status = 200;
		this.errors = errors;
	}
}
