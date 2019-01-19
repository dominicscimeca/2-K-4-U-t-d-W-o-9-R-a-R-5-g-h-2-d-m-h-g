package com.disney.studios;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.disney.studios.dogimage.vote.DogImageNotFoundException;
import com.disney.studios.dogimage.vote.UnauthorizedException;
import com.disney.studios.dogimage.vote.UserNotFoundException;
import com.disney.studios.dogimage.vote.VoteDeniedException;
import com.disney.studios.user.InvalidLoginException;
import com.disney.studios.user.NotAValidEmailException;
import com.disney.studios.user.UserAlreadyRegisteredException;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	class ExceptionDetails {
		@Getter private final Date timestamp;
		@Getter private final int status;
		@Getter private final String error;
		@Getter private final String message;
		@Getter private final String path;

		ExceptionDetails(Exception exception, HttpStatus status, String path){
			this.timestamp = new Date();
			this.status = status.value();
			this.error = exception.getClass().getCanonicalName();
			this.message = exception.getMessage();
			this.path = path;
		}
	}


	@ExceptionHandler(value = { UnauthorizedException.class, InvalidLoginException.class, JWTDecodeException.class})
	protected ResponseEntity<Object> handleUnauthorizedRequest(RuntimeException ex, ServletWebRequest request) {
		return handleException(ex, request, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(value = { DogImageNotFoundException.class, UserNotFoundException.class })
	protected ResponseEntity<Object> handleNotFoundRequest(RuntimeException ex, ServletWebRequest request) {
		return handleException(ex, request, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { VoteDeniedException.class })
	protected ResponseEntity<Object> handleForbidden(RuntimeException ex, ServletWebRequest request) {
		return handleException(ex, request, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(value = { UserAlreadyRegisteredException.class, NotAValidEmailException.class })
	protected ResponseEntity<Object> handleBadREquest(RuntimeException ex, ServletWebRequest request) {
		return handleException(ex, request, HttpStatus.BAD_REQUEST);
	}

	private ResponseEntity<Object> handleException(RuntimeException ex, ServletWebRequest request, HttpStatus status) {
		ExceptionDetails bodyOfResponse = new ExceptionDetails(ex, status, request.getRequest().getRequestURI());

		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), status, request);
	}

}

