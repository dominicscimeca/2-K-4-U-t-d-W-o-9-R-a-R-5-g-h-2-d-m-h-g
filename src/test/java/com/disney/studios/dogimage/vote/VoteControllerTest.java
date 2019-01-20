package com.disney.studios.dogimage.vote;

import com.disney.studios.dogimage.vote.exception.UnauthorizedException;
import com.disney.studios.user.User;
import com.disney.studios.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(VoteController.class)
public class VoteControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private VoteService voteService;

	@MockBean
	private UserService userService;

	private String notValidAuthHeader;
	private String validAuthHeader;
	private String email;
	private User user;

	@Before
	public void setUp(){
		this.notValidAuthHeader = "not-valid-auth-header";
		this.validAuthHeader = "valid-auth-header";
		this.email = "me@me.com";
		this.user = new User(this.email);

		when(this.userService.getUserByAuthHeader(notValidAuthHeader)).thenThrow(UnauthorizedException.class);
		when(this.userService.getUserByAuthHeader(validAuthHeader)).thenReturn(this.user);
	}

	@Test
	public void shouldHaveCorrectResponseForBadToken() throws Exception {
		//then
		this.mockMvc.perform(
				post("/dogs/1/vote/down")
						.header("Authorization",this.notValidAuthHeader)
		)
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void shouldVoteUpCorrectly() throws Exception {
		this.mockMvc.perform(
				post("/dogs/1/vote/up")
						.header("Authorization",this.validAuthHeader)
		)
				.andExpect(status().isOk());

		//then
		verify(voteService, times(1)).voteUp(1, this.user);
	}

	@Test
	public void shouldVoteDownCorrectly() throws Exception {
		this.mockMvc.perform(
				post("/dogs/2/vote/down")
						.header("Authorization",this.validAuthHeader)
		)
				.andExpect(status().isOk());

		//then
		verify(voteService, times(1)).voteDown(2, this.user);
	}
}

