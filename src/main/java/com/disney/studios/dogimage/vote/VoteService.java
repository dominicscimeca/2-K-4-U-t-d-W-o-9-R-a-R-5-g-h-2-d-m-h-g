package com.disney.studios.dogimage.vote;

import com.disney.studios.dogimage.DogImageDTO;
import com.disney.studios.dogimage.DogImageService;
import com.disney.studios.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteService {
	@Autowired private final VoteRepository voteRepository;
	@Autowired private final DogImageService dogImageService;

	public Vote voteUp(Integer imageId, User user) {
		return this.vote(imageId, Vote.UP, user);
	}

	public Vote voteDown(Integer imageId, User user) {
		return this.vote(imageId, Vote.DOWN, user);
	}

	private Vote vote(Integer imageId, Integer voteUpdate, User user){
		Optional<DogImageDTO> dog = this.dogImageService.getDogImage(imageId);

		if(dog.isPresent()){
			Optional<Vote> existingVote = this.voteRepository.findByDogAndUser(imageId, user.getId());
			if(existingVote.isPresent()){
				throw new ExistingVoteException();
			}

			Vote vote = new Vote(imageId, voteUpdate, user.getId());

			return this.voteRepository.save(vote);
		}else{
			throw new DogImageNotFoundException();
		}
	}
}
