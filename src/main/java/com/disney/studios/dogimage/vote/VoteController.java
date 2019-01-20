package com.disney.studios.dogimage.vote;

import com.disney.studios.user.User;
import com.disney.studios.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class VoteController {
	private final VoteService voteService;
	private final UserService userService;

	@RequestMapping(path = "/dogs/{imageId}/vote/up", method = RequestMethod.POST)
	public void voteUp(@RequestHeader(value="Authorization") String authorizationHeader, @PathVariable Integer imageId) {
		User user = this.userService.getUserByAuthHeader(authorizationHeader);

		this.voteService.voteUp(imageId, user);
	}

	@RequestMapping(path = "/dogs/{imageId}/vote/down", method = RequestMethod.POST)
	public void voteDown(@RequestHeader(value="Authorization") String authorizationHeader, @PathVariable Integer imageId) {
		User user = this.userService.getUserByAuthHeader(authorizationHeader);

		this.voteService.voteDown(imageId, user);
	}
}
