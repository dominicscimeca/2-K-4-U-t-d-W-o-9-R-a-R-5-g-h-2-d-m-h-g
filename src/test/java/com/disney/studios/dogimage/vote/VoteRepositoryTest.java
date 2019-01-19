package com.disney.studios.dogimage.vote;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VoteRepositoryTest {
	@Autowired
	private VoteRepository voteRepository;

	@Test
	public void findByDogImageAndUser(){
		//given
		Integer dogId = 5;
		Integer userId = 9;
		Vote vote = new Vote(dogId, Vote.DOWN, userId);
		this.voteRepository.save(vote);

		//when
		Optional<Vote> response = this.voteRepository.findByDogAndUser(dogId, userId);

		//then
		assertThat(response.get()).isEqualTo(vote);
	}
}