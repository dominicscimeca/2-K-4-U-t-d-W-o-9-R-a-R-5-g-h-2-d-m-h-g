package com.disney.studios.user;

public class UserAlreadyRegisteredException extends RuntimeException {
	public UserAlreadyRegisteredException(String message){
		super(message);
	}
}
