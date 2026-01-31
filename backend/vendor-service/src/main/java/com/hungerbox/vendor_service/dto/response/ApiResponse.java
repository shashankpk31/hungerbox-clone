package com.hungerbox.vendor_service.dto.response;

import java.time.LocalDateTime;

public class ApiResponse {
	private boolean success;
	private String message;
	private Object data;
	private String error;
	private LocalDateTime timestamp;

	public ApiResponse() {
		this.timestamp = LocalDateTime.now();
	}

	public ApiResponse(boolean success, String message, Object data) {
		this();
		this.success = success;
		this.message = message;
		this.data = data;
	}

	public ApiResponse(boolean success, String message, String error) {
		this();
		this.success = success;
		this.message = message;
		this.error = error;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}
