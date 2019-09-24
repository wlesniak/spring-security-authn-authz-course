package com.pluralsight.security.exceptions;

public class InvalidTOTPVerificationCode extends RuntimeException {

	public InvalidTOTPVerificationCode(String msg) {
		super(msg);
	}
	
	public InvalidTOTPVerificationCode(String msg, Throwable ex) {
		super(msg,ex);
	}
	
}
