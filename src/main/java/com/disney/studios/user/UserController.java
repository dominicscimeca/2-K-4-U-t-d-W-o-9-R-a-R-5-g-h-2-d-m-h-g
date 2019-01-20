package com.disney.studios.user;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {
	private final UserService userService;

	@ApiOperation(
			value = "User Login",
			notes = "If credentials are correct: returns a valid jwt token, the email address it is for, and when it will expire"
	)
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public UserToken login(String email, String password) {
		return this.userService.login(email, password);
	}

	@ApiOperation(
			value = "User Registration",
			notes = "Creates a new account (which can be used to login). Then returns a valid jwt token, the email address it is for, and when it will expire"
	)
	@RequestMapping(path = "/register", method = RequestMethod.POST)
	public UserToken register(String email, String password) {
		return this.userService.register(email, password);
	}
}
