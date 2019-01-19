package com.disney.studios.dogimage.vote;

import com.disney.studios.user.User;
import com.disney.studios.user.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class VoteController {
	private final UserService userService;
	private final VoteService voteService;

	public VoteController(VoteService voteService, UserService userService) {
		this.voteService = voteService;
		this.userService = userService;
	}

	@RequestMapping(path = "/dogs/{imageId}/vote/up", method = RequestMethod.POST)
	public void voteUp(@RequestHeader(value="Authorization") String authorizationHeader, @PathVariable Integer imageId) {
		String token = getTokenFromHeader(authorizationHeader);
		User user = this.userService.getUserFromToken(token);

		this.voteService.voteUp(imageId, user);
	}

	@RequestMapping(path = "/dogs/{imageId}/vote/down", method = RequestMethod.POST)
	public void voteDown(@RequestHeader(value="Authorization") String authorizationHeader, @PathVariable Integer imageId) {
		String token = getTokenFromHeader(authorizationHeader);
		User user = this.userService.getUserFromToken(token);

		this.voteService.voteDown(imageId, user);
	}

	private String getTokenFromHeader(String authHeader){
		return authHeader.split(" ")[1];
	}
}
