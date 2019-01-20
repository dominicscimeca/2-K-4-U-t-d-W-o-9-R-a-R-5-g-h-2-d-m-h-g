package com.disney.studios.dogimage.vote;

import com.disney.studios.user.User;
import com.disney.studios.user.UserService;
import com.disney.studios.user.auth.Auth;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class VoteController {
	private final VoteService voteService;
	private final UserService userService;

	@ApiOperation(
			value = "Vote Up an image of a dog",
			notes = "Increments the vote count of an image of dog, retrieved by id. Can only be done once per user."
	)
	@Auth
	@RequestMapping(path = "/dogs/{imageId}/vote/up", method = RequestMethod.POST)
	public void voteUp(@RequestHeader(value="Authorization") String authorizationHeader, @PathVariable Integer imageId, @RequestParam(required = false) User user) {
		this.voteService.voteUp(imageId, user);
	}

	@ApiOperation(
			value = "Vote Down an image of a dog",
			notes = "Decrements the vote count of an image of dog, retrieved by id. Can only be done once per user."
	)
	@RequestMapping(path = "/dogs/{imageId}/vote/down", method = RequestMethod.POST)
	public void voteDown(@RequestHeader(value="Authorization") String authorizationHeader, @PathVariable Integer imageId) {
		User user = this.userService.getUserByAuthHeader(authorizationHeader);

		this.voteService.voteDown(imageId, user);
	}
}
