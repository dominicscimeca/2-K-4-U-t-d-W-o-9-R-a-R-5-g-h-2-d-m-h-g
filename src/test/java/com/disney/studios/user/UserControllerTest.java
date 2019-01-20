package com.disney.studios.user;

import com.disney.studios.InstantService;
import com.disney.studios.user.exception.InvalidLoginException;
import com.disney.studios.user.exception.NotAValidEmailException;
import com.disney.studios.user.exception.UserAlreadyRegisteredException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Instant;
import java.util.Date;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@MockBean
	private UserService fakeUserService;

	@MockBean
	private InstantService instantService;

	@Autowired
	private MockMvc mockMvc;

	@Before
	public void setUp(){
		Instant instant = Instant.ofEpochMilli(1547967301000L);
		when(this.instantService.getInstantNow()).thenReturn(instant);
	}

	@Test
	public void shouldLogin() throws Exception {
		//given
		String email = "1234@344";
		String password = "secret";
		String token = "valid-token";
		UserToken userToken = new UserToken(email, token, new Date(1547967301000L));
		when(this.fakeUserService.login(email, password)).thenReturn(userToken);

		//when
		MvcResult result = this.mockMvc.perform(
				post("/login")
						.param("email", email)
						.param("password", password)
		)
				.andExpect(status().isOk())
				.andReturn();

		//then
		assertThat(result.getResponse().getContentAsString()).isEqualTo("{\"email\":\"1234@344\",\"token\":\"valid-token\",\"expiresAt\":\"2019-01-20T06:55:01.000+0000\"}");
	}

	@Test
	public void shouldRegister() throws Exception {
		//given
		String email = "1234@344";
		String password = "secret";
		String token = "valid-token";
		UserToken userToken = new UserToken(email, token, new Date(1547967301000L));
		when(this.fakeUserService.register(email, password)).thenReturn(userToken);

		//when
		MvcResult result = this.mockMvc.perform(
				post("/register")
						.param("email", email)
						.param("password", password)
		)
				.andExpect(status().isOk())
				.andReturn();

		//then
		assertThat(result.getResponse().getContentAsString()).isEqualTo("{\"email\":\"1234@344\",\"token\":\"valid-token\",\"expiresAt\":\"2019-01-20T06:55:01.000+0000\"}");
	}

	@Test
	public void loginFailure() throws Exception {
		//given
		String email = "1234@344";
		String password = "secret";
		when(this.fakeUserService.login(email, password)).thenThrow(InvalidLoginException.class);

		//when
		this.mockMvc.perform(
				post("/login")
						.param("email", email)
						.param("password", password)
		)
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void registerUserAlreadyExistsFailure() throws Exception {
		//given
		String email = "1234@344";
		String password = "secret";
		when(this.fakeUserService.register(email, password)).thenThrow(UserAlreadyRegisteredException.class);

		//when
		this.mockMvc.perform(
				post("/register")
						.param("email", email)
						.param("password", password)
		)
				.andExpect(status().isBadRequest());
	}

	@Test
	public void registerUserNotAnEmail() throws Exception {
		//given
		String email = "";
		String password = "secret";
		when(this.fakeUserService.register(email, password)).thenThrow(NotAValidEmailException.class);

		//when
		this.mockMvc.perform(
				post("/register")
						.param("email", email)
						.param("password", password)
		)
				.andExpect(status().isBadRequest());
	}
}
