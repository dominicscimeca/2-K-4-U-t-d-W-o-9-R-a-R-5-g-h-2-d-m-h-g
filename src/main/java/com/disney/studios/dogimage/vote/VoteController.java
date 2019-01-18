package com.disney.studios.dogimage.vote;

import com.disney.studios.user.User;
import com.disney.studios.user.UserService;
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
	public void voteUp(String url) throws MalformedURLException {
		User user = this.userService.getUser();
		this.voteService.voteUp(new URL(url), user);
	}

	public void voteDown(String url) throws MalformedURLException {
		User user = this.userService.getUser();
		this.voteService.voteDown(new URL(url), user);
	}
}
