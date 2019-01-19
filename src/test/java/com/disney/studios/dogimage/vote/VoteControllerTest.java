package com.disney.studios.dogimage.vote;

import com.disney.studios.user.JWTProvider;
import com.disney.studios.user.User;
import com.disney.studios.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.MalformedURLException;
import java.net.URL;

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
	public void shouldUseVoteServiceToVoteUp() throws MalformedURLException {
		//given
		String url = "http://google.com";
		String token = "valid-token";
		String authorizationHeader = "Bearer " + token;
		when(userService.getUserFromToken(token)).thenReturn(this.user);

		//when
		this.voteController.voteUp(authorizationHeader, url);

		//then
		verify(fakeVoteService, times(1)).voteUp(new URL(url), this.user);
	}

	@Test
	public void shouldUseVoteServiceToVoteDown() throws MalformedURLException {
		//given
		String url = "http://google.com";
		String token = "valid-token";
		String authorizationHeader = "Bearer " + token;
		when(userService.getUserFromToken(token)).thenReturn(user);

		//when
		this.voteController.voteDown(authorizationHeader, url);

		//then
		verify(fakeVoteService, times(1)).voteDown(new URL(url), this.user);
	}
}
