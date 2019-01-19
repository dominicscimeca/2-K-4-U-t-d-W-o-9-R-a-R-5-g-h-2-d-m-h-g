package com.disney.studios.dogimage;

import com.disney.studios.dogimage.vote.Vote;
import com.disney.studios.dogimage.vote.VoteRepository;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DogImageRepositoryTest {

	@Autowired
	private DogImageRepository dogImageRepository;

	@Autowired
	private VoteRepository voteRepository;

	@Test
	public void findDTOById() throws MalformedURLException {
		//given
		Integer userId = 10;
		URL url = new URL("http://google.com");
		String breed = "Dalmation";
		DogImage dogImage = this.dogImageRepository.save(new DogImage(url, breed));
		DogImageDTO expectedDogImageDTO = new DogImageDTO(dogImage.getId(), dogImage.getUrl(), dogImage.getBreed(), -1L);

		this.voteRepository.save(new Vote(dogImage.getId(), Vote.DOWN, userId));
		this.voteRepository.save(new Vote(dogImage.getId(), Vote.DOWN, userId));
		this.voteRepository.save(new Vote(dogImage.getId(), Vote.UP, userId));
		this.voteRepository.save(new Vote(100, Vote.DOWN, userId));

		//when
		Optional<DogImageDTO> response = this.dogImageRepository.findDTOById(dogImage.getId());

		System.out.println(response);


		//then
		assertThat(response.get()).isEqualTo(expectedDogImageDTO);
	}

	@Test
	public void findByBreed() throws MalformedURLException {
		//given
		Integer userId = 9;
		String breed = "Dalmation";
		URL url = new URL("http://google.com");
		URL url2 = new URL("http://google2.com");
		DogImage dalmationDogImage = this.dogImageRepository.save(new DogImage(url, breed));
		DogImage dalmationDogImage2 = this.dogImageRepository.save(new DogImage(url2, breed));
		this.dogImageRepository.save(new DogImage(url, "German"));

		this.voteRepository.save(new Vote(dalmationDogImage.getId(), Vote.DOWN, userId));
		this.voteRepository.save(new Vote(dalmationDogImage.getId(), Vote.DOWN, userId));
		this.voteRepository.save(new Vote(dalmationDogImage.getId(), Vote.UP, userId));
		this.voteRepository.save(new Vote(100, Vote.DOWN, userId));

		DogImageDTO expectedDalmationDogImageDTO = new DogImageDTO(dalmationDogImage, -1L);
		DogImageDTO expectedDalmationDogImageDTO2 = new DogImageDTO(dalmationDogImage2, null);

		//when
		Iterable<DogImageDTO> response = this.dogImageRepository.findAllDTOByBreed(breed);

		System.out.println(Lists.newArrayList(response));

		response.forEach(rep -> System.out.println(rep.getVoteCount()));

		//then
		Iterator<DogImageDTO> responseIterator = response.iterator();

		assertThat(responseIterator.next().getVoteCount()).isEqualTo(expectedDalmationDogImageDTO2.getVoteCount());
		assertThat(responseIterator.next().getVoteCount()).isEqualTo(expectedDalmationDogImageDTO.getVoteCount());
		assertThat(responseIterator.hasNext()).isEqualTo(false);
	}


}