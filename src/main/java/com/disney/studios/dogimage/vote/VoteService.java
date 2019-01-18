package com.disney.studios.dogimage.vote;

import com.disney.studios.dogimage.DogImage;
import com.disney.studios.dogimage.DogImageService;
import com.disney.studios.user.User;
import com.disney.studios.user.UserService;

import java.net.URL;

public class VoteService {
	private final VoteRepository voteRepository;
	private final DogImageService dogImageService;

	public VoteService(VoteRepository voteRepository, DogImageService dogImageService) {
		this.dogImageService = dogImageService;
		this.voteRepository = voteRepository;
	}

	public void voteUp(URL url, User user) {
		DogImage dog = this.dogImageService.getDogImageByURL(url);

		Vote vote = new Vote(dog, Vote.UP, user);

		this.voteRepository.save(vote);
	}

	public void voteDown(URL url, User user) {
		DogImage dog = this.dogImageService.getDogImageByURL(url);

		Vote vote = new Vote(dog, Vote.DOWN, user);

		this.voteRepository.save(vote);
	}
}
