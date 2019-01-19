package com.disney.studios.dogimage.vote;

import com.disney.studios.dogimage.DogImage;
import com.disney.studios.dogimage.DogImageService;
import com.disney.studios.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteService {
	private final VoteRepository voteRepository;
	private final DogImageService dogImageService;

	public Vote voteUp(Integer imageId, User user) {
		return this.vote(imageId, Vote.UP, user);
	}

	public Vote voteDown(Integer imageId, User user) {
		return this.vote(imageId, Vote.DOWN, user);
	}

	private Vote vote(Integer imageId, Integer voteUpdate, User user){
		Optional<DogImage> dog = this.dogImageService.getDogImage(imageId);

		if(dog.isPresent()){
			Vote vote = new Vote(imageId, voteUpdate, user.getId());

			return this.voteRepository.save(vote);
		}else{
			throw new DogImageNotFoundException();
		}
	}
}
