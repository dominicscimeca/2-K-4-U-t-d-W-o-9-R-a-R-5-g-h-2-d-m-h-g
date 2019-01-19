package com.disney.studios.dogimage.vote.exception;

public class UnauthorizedException extends RuntimeException {
	public UnauthorizedException(String message){
		super(message);
	}
}
