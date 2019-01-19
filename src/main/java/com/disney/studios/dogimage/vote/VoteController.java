package com.disney.studios.dogimage.vote;

import com.disney.studios.user.JWTProvider;
import com.disney.studios.user.User;
import com.disney.studios.user.UserService;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.MalformedURLException;
import java.net.URL;

public class VoteController {
	private final UserService userService;
	private final VoteService voteService;

	public VoteController(VoteService voteService, UserService userService) {
		this.voteService = voteService;
		this.userService = userService;
	}

	@RequestMapping(path = "/dogs/{url}/vote/up", method = RequestMethod.POST)
	public void voteUp(@RequestHeader(value="Authorization") String authorizationHeader, String url) throws MalformedURLException {
		String token = getTokenFromHeader(authorizationHeader);
		User user = this.userService.getUserFromToken(token);

		this.voteService.voteUp(new URL(url), user);
	}

	@RequestMapping(path = "/dogs/{url}/vote/down", method = RequestMethod.POST)
	public void voteDown(@RequestHeader(value="Authorization") String authorizationHeader, String url) throws MalformedURLException {
		String token = getTokenFromHeader(authorizationHeader);
		User user = this.userService.getUserFromToken(token);

		this.voteService.voteDown(new URL(url), user);
	}

	private String getTokenFromHeader(String authHeader){
		return authHeader.split(" ")[1];
	}
}
