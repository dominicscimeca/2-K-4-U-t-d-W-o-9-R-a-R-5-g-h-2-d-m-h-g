package com.disney.studios.user.exception;

public class InvalidLoginException extends RuntimeException {
	public InvalidLoginException(String message){
		super(message);
	}
}
