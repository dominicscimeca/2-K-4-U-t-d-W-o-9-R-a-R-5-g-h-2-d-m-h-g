package com.disney.studios.dogimage.vote;

import com.disney.studios.dogimage.DogImageDTO;
import com.disney.studios.dogimage.DogImageService;
import com.disney.studios.dogimage.vote.exception.DogImageNotFoundException;
import com.disney.studios.dogimage.vote.exception.VoteDeniedException;
import com.disney.studios.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VoteServiceTest {
	private VoteService voteService;
	private VoteRepository fakeVoteRepository;
	private DogImageService fakeDogImageService;
	private String url;
	private DogImageDTO dog;
	private User user;
	private Integer id;

	@Before
	public void setUp() {
		this.fakeVoteRepository = mock(VoteRepository.class);
		this.fakeDogImageService = mock(DogImageService.class);
		this.voteService = new VoteService(fakeVoteRepository, fakeDogImageService);

		this.url = "http://google.com";
		this.id = 7;
		this.dog = new DogImageDTO(id, url, "Breed 1", 1L);
		this.user = new User("email@email.com");
	}

	@Test
	public void shouldSaveVoteForVoteUp() {
		//given
		Vote expectedVote = new Vote(id, Vote.UP, user.getId());
		when(this.fakeDogImageService.getDogImageOptional(id)).thenReturn(Optional.of(dog));
		when(this.fakeVoteRepository.findByDogAndUser(id, null)).thenReturn(Optional.empty());

		//when
		voteService.voteUp(id, user);

		//then
		verify(fakeVoteRepository, times(1)).save(expectedVote);
	}

	@Test
	public void shouldSaveVoteForVoteDown() {
		//given
		Vote expectedVote = new Vote(id, Vote.DOWN, user.getId());
		when(this.fakeDogImageService.getDogImageOptional(id)).thenReturn(Optional.of(dog));
		when(this.fakeVoteRepository.findByDogAndUser(id, null)).thenReturn(Optional.empty());

		//when
		voteService.voteDown(id, user);

		//then
		verify(fakeVoteRepository, times(1)).save(expectedVote);
	}

	@Test(expected = VoteDeniedException.class)
	public void shouldSaveVoteForVoteUpExistingVoteFromUser() {
		//given
		Vote expectedVote = new Vote(id, Vote.DOWN, user.getId());
		when(this.fakeDogImageService.getDogImageOptional(id)).thenReturn(Optional.of(dog));
		when(this.fakeVoteRepository.findByDogAndUser(id, null)).thenReturn(Optional.of(expectedVote));

		//when
		voteService.voteUp(id, user);
	}

	@Test(expected = VoteDeniedException.class)
	public void shouldSaveVoteForVoteDownExistingVoteFromUser() {
		//given
		Vote expectedVote = new Vote(id, Vote.DOWN, user.getId());
		when(this.fakeDogImageService.getDogImageOptional(id)).thenReturn(Optional.of(dog));
		when(this.fakeVoteRepository.findByDogAndUser(id, null)).thenReturn(Optional.of(expectedVote));

		//when
		voteService.voteDown(id, user);
	}


	@Test(expected = DogImageNotFoundException.class)
	public void shouldThrowAnErrorIfImageDoesntExistVoteUp() {
		voteService.voteUp(id, user);
	}

	@Test(expected = DogImageNotFoundException.class)
	public void shouldThrowAnErrorIfImageDoesntExistVoteDown() {
		voteService.voteDown(id, user);
	}
}
