package com.david.dto;

public class ResponseDTO {
	private String value;
	private Throwable exception;

	public ResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseDTO(String value, Throwable exception) {
		super();
		this.value = value;
		this.exception = exception;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}

	@Override
	public String toString() {
		return "ErrorResponse [value=" + value + ", exception=" + exception + "]";
	}

}
