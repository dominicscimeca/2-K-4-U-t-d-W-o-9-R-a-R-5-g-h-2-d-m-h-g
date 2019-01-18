package com.disney.studios.dogimage.vote;

import com.disney.studios.user.User;
import com.disney.studios.user.UserService;
import com.disney.studios.dogimage.DogBreed;
import com.disney.studios.dogimage.DogImage;
import com.disney.studios.dogimage.DogImageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.MalformedURLException;
import java.net.URL;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VoteServiceTest {
	private VoteService voteService;
	private VoteRepository fakeVoteRepository;
	private DogImageService fakeDogImageService;
	private URL url;
	private DogImage dog;
	private User user;

	@Before
	public void setUp() throws MalformedURLException {
		this.fakeVoteRepository = mock(VoteRepository.class);
		this.fakeDogImageService = mock(DogImageService.class);
		this.voteService = new VoteService(fakeVoteRepository, fakeDogImageService);

		this.url = new URL("http://google.com");
		this.dog = new DogImage(url, new DogBreed("Breed 1"));
		this.user = new User("email@email.com");

		when(this.fakeDogImageService.getDogImageByURL(url)).thenReturn(this.dog);
	}

	@Test
	public void shouldSaveVoteForVoteUp() {
		//given
		Vote expectedVote = new Vote(dog, Vote.UP, user);

		//when
		voteService.voteUp(url, user);

		//then
		verify(fakeVoteRepository, times(1)).save(expectedVote);
	}

	@Test
	public void shouldSaveVoteForVoteDown() {
		//given
		Vote expectedVote = new Vote(dog, Vote.DOWN, user);

		//when
		voteService.voteDown(url, user);

		//then
		verify(fakeVoteRepository, times(1)).save(expectedVote);
	}
}
