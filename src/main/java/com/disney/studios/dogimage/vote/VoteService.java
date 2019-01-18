package com.disney.studios.dogimage.vote;

import com.disney.studios.dogimage.DogImage;
import com.disney.studios.dogimage.DogImageService;
import com.disney.studios.user.User;
import com.disney.studios.user.UserService;

import java.net.URL;

public class VoteService {
	private final VoteRepository voteRepository;
	private final DogImageService dogImageService;
	private final UserService userService;

	public VoteService(VoteRepository voteRepository, DogImageService dogImageService, UserService userService) {
		this.userService = userService;
		this.dogImageService = dogImageService;
		this.voteRepository = voteRepository;
	}

	public void voteUp(URL url) {
		DogImage dog = this.dogImageService.getDogImageByURL(url);
		User user = this.userService.getUser();

		Vote vote = new Vote(dog, Vote.UP, user);

		this.voteRepository.save(vote);
	}

	public void voteDown(URL url) {
		DogImage dog = this.dogImageService.getDogImageByURL(url);
		User user = this.userService.getUser();

		Vote vote = new Vote(dog, Vote.DOWN, user);

		this.voteRepository.save(vote);
	}
}
