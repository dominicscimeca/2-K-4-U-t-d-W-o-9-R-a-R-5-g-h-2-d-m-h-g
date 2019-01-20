package com.disney.studios.dogimage.vote;

import com.disney.studios.user.User;
import com.disney.studios.user.auth.Auth;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VoteController {
	private final VoteService voteService;

	@ApiOperation(
			value = "Vote Up an image of a dog",
			notes = "Increments the vote count of an image of dog, retrieved by id. Can only be done once per user."
	)
	@Auth
	@ApiImplicitParam(name = "Authorization", paramType = "header")
	@RequestMapping(path = "/dogs/{imageId}/vote/up", method = RequestMethod.POST)
	public void voteUp(@PathVariable Integer imageId, User user) {
		this.voteService.voteUp(imageId, user);
	}

	@ApiOperation(
			value = "Vote Down an image of a dog",
			notes = "Decrements the vote count of an image of dog, retrieved by id. Can only be done once per user."
	)
	@Auth
	@ApiImplicitParam(name = "Authorization", paramType = "header")
	@RequestMapping(path = "/dogs/{imageId}/vote/down", method = RequestMethod.POST)
	public void voteDown(@PathVariable Integer imageId, User user) {
		this.voteService.voteDown(imageId, user);
	}
}
