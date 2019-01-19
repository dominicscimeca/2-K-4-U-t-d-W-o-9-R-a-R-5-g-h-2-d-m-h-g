package com.disney.studios.dogimage;

import com.disney.studios.dogimage.vote.Vote;
import com.disney.studios.dogimage.vote.VoteRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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
	private TestEntityManager entityManager;

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
		URL url = new URL("http://google.com");
		String breed = "Dalmation";
		DogImage dalmationDogImage = this.dogImageRepository.save(new DogImage(url, breed));
		DogImage dalmationDogImage2 = this.dogImageRepository.save(new DogImage(url, breed));
		DogImage germanDogImage = this.dogImageRepository.save(new DogImage(url, "German"));

		//when
		Iterable<DogImage> response = this.dogImageRepository.findAllByBreed(breed);

		//then
		Iterator<DogImage> responseIterator = response.iterator();

		assertThat(responseIterator.next()).isEqualTo(dalmationDogImage);
		assertThat(responseIterator.next()).isEqualTo(dalmationDogImage2);
		assertThat(responseIterator.hasNext()).isEqualTo(false);
	}


}