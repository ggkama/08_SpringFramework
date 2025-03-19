package com.kh.spring.exception;

public class PasswordNotMatchException extends RuntimeException{

	public PasswordNotMatchException(String message) {
		super(message);
}
}
