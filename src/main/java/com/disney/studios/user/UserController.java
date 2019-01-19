package com.disney.studios.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@RequestMapping("/login")
	public String login(String email, String password) {
		return this.userService.login(email, password);
	}

	@RequestMapping("/register")
	public String register(String email, String password) {
		return this.userService.register(email, password);
	}
}
