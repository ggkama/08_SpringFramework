package com.kh.spring.exception;

public class DuplicateIdException extends RuntimeException{
	
	public DuplicateIdException(String message) {
		super(message);
	}
}
