package com.disney.studios.dogimage.vote;

import com.disney.studios.InstantService;
import com.disney.studios.dogimage.vote.exception.UnauthorizedException;
import com.disney.studios.dogimage.vote.exception.VoteDeniedException;
import com.disney.studios.user.User;
import com.disney.studios.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class VoteControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private VoteService voteService;

	@MockBean
	private UserService userService;

	@MockBean
	private InstantService instantService;

	private String notValidAuthHeader;
	private String validAuthHeader;
	private String email;
	private User user;
	private Instant instant;

	@Before
	public void setUp() {
		this.notValidAuthHeader = "not-valid-auth-header";
		this.validAuthHeader = "valid-auth-header";
		this.email = "me@me.com";
		this.user = new User(this.email);
		this.instant = Instant.ofEpochMilli(1547967301000L);

		when(this.userService.getUserByAuthHeader(notValidAuthHeader)).then(answer -> {throw new UnauthorizedException("Unauthorized Exception Message");});
		when(this.userService.getUserByAuthHeader(validAuthHeader)).thenReturn(this.user);
		when(this.instantService.getInstantNow()).thenReturn(this.instant);
	}

	@Test
	public void shouldHaveCorrectResponseForBadToken() throws Exception {
		when(voteService.voteDown(eq(2), isA(User.class))).thenThrow(VoteDeniedException.class);

		//then
		this.mockMvc.perform(
				post("/dogs/1/vote/down")
						.header("Authorization",this.notValidAuthHeader)
		)
				.andExpect(status().isUnauthorized())
				.andExpect(content().string("{\"timestamp\":1547967301.000000000,\"status\":401,\"error\":\"UnauthorizedException\",\"message\":\"Unauthorized Exception Message\",\"path\":\"/dogs/1/vote/down\"}"));
	}

	@Test
	public void shouldVoteUpCorrectly() throws Exception {
		this.mockMvc.perform(
				post("/dogs/1/vote/up")
						.header("Authorization",this.validAuthHeader)
		)
				.andExpect(status().isOk());

		//then
		verify(voteService, times(1)).voteUp(eq(1), isA(User.class));
	}

	@Test
	public void shouldVoteDownCorrectly() throws Exception {
		this.mockMvc.perform(
				post("/dogs/2/vote/down")
						.header("Authorization",this.validAuthHeader)
		)
				.andExpect(status().isOk());

		//then
		verify(voteService, times(1)).voteDown(eq(2), isA(User.class));
	}

	@Test
	public void shouldNotBeAbleToVoteTwice() throws Exception {
		when(voteService.voteDown(eq(2), isA(User.class))).thenThrow(VoteDeniedException.class);

		this.mockMvc.perform(
				post("/dogs/2/vote/down")
						.header("Authorization",this.validAuthHeader)
		)
				.andExpect(status().isForbidden());

	}
}

