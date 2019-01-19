package com.disney.studios.user;

public class InvalidLoginException extends RuntimeException {
	public InvalidLoginException(String message){
		super(message);
	}
}
