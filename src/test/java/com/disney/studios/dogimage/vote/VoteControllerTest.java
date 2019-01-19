package com.disney.studios.dogimage.vote;

import com.disney.studios.user.User;
import com.disney.studios.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VoteControllerTest {
	private VoteService fakeVoteService;
	private UserService userService;
	private VoteController voteController;
	private User user;
	private String email;

	@Before
	public void setUp(){
		this.fakeVoteService = mock(VoteService.class);
		this.userService = mock(UserService.class);
		this.voteController = new VoteController(this.fakeVoteService, this.userService);
		this.email = "email@email.com";
		this.user = new User(email);
	}

	@Test
	public void shouldUseVoteServiceToVoteUp() {
		//given
		Integer id = 5;
		String token = "valid-token";
		String authorizationHeader = "Bearer " + token;
		when(userService.getUserFromToken(token)).thenReturn(this.user);

		//when
		this.voteController.voteUp(authorizationHeader, id);

		//then
		verify(fakeVoteService, times(1)).voteUp(id, this.user);
	}

	@Test
	public void shouldUseVoteServiceToVoteDown() {
		//given
		Integer id = 5;
		String token = "valid-token";
		String authorizationHeader = "Bearer " + token;
		when(userService.getUserFromToken(token)).thenReturn(user);

		//when
		this.voteController.voteDown(authorizationHeader, id);

		//then
		verify(fakeVoteService, times(1)).voteDown(id, this.user);
	}

	@Test
	public void handlesUserNotFound() {
		//given
		Integer id = 5;
		String token = "valid-token";
		String authorizationHeader = "Bearer " + token;
		when(userService.getUserFromToken(token)).thenThrow(UserNotFoundException.class);

		//when
		this.voteController.voteDown(authorizationHeader, id);

		//then
		verify(fakeVoteService, times(1)).voteDown(id, this.user);
	}
}
