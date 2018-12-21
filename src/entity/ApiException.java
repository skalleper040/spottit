package entity;

import javax.ws.rs.core.Response.Status;

public class ApiException extends Exception {

	private Status statusCode;
	private String message;
	
	public ApiException (Status statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}
	
	public Status getStatus() {
		return statusCode;
	}
	
	public String getMessage() {
		return message;
	}
	
}
