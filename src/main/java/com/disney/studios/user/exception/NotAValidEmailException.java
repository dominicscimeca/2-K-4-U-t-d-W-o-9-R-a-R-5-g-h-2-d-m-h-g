package com.disney.studios.user.exception;

public class NotAValidEmailException extends RuntimeException {
	public NotAValidEmailException(String message){
		super(message);
	}
}
