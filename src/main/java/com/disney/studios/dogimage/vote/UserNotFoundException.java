package com.disney.studios.dogimage.vote;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(String message){
		super(message);
	}
}
