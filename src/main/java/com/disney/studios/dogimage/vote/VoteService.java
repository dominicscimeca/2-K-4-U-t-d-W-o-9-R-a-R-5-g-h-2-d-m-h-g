package com.disney.studios.dogimage.vote;

import com.disney.studios.dogimage.DogImageDTO;
import com.disney.studios.dogimage.DogImageService;
import com.disney.studios.dogimage.vote.exception.DogImageNotFoundException;
import com.disney.studios.dogimage.vote.exception.VoteDeniedException;
import com.disney.studios.user.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteService {
	private final Logger log = LoggerFactory.getLogger(VoteService.class);

	private final VoteRepository voteRepository;
	private final DogImageService dogImageService;

	public Vote voteUp(Integer imageId, User user) {
		return this.vote(imageId, Vote.UP, user);
	}

	public Vote voteDown(Integer imageId, User user) {
		return this.vote(imageId, Vote.DOWN, user);
	}

	private Vote vote(Integer imageId, Integer voteUpdate, User user){
		validateDogImageExists(imageId, user);
		validateUserVotingForThisDogBefore(imageId, user);

		Vote vote = new Vote(imageId, voteUpdate, user.getId());

		log.info("Creating a new vote. vote={}", vote);
		return this.voteRepository.save(vote);
	}

	private void validateDogImageExists(Integer imageId, User user) {
		Optional<DogImageDTO> dog = this.dogImageService.getDogImageOptional(imageId);

		if(!dog.isPresent()) {
			String errorMessage = String.format("Dog Image Not Found. Vote Invalid. imageID='%s' email='%s'", imageId, user.getEmail());
			log.warn(errorMessage);
			throw new DogImageNotFoundException(errorMessage);
		}
	}

	private void validateUserVotingForThisDogBefore(Integer imageId, User user) {
		Optional<Vote> existingVote = this.voteRepository.findByDogAndUser(imageId, user.getId());

		if(existingVote.isPresent()){
			String errorMessage = String.format("Vote Denied. You can only vote once on any particular dog image. imageID='%s' email='%s'", imageId, user.getEmail());
			log.warn(errorMessage);
			throw new VoteDeniedException(errorMessage);
		}
	}
}
