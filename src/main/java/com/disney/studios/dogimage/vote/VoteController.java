package com.disney.studios.dogimage.vote;

import com.disney.studios.user.User;
import com.disney.studios.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class VoteController {
	@Autowired private final VoteService voteService;
	@Autowired private final UserService userService;

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
