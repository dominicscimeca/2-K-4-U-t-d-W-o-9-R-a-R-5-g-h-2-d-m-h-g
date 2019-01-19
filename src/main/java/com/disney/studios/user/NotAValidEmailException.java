package com.disney.studios.user;

public class NotAValidEmailException extends RuntimeException {
	public NotAValidEmailException(String message){
		super(message);
	}
}
