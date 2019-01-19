package com.disney.studios.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {
	private final UserService userService;

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public String login(String email, String password) {
		return this.userService.login(email, password);
	}

	@RequestMapping(path = "/register", method = RequestMethod.POST)
	public String register(String email, String password) {
		return this.userService.register(email, password);
	}
}
