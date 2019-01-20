package com.disney.studios.user;

import com.disney.studios.user.exception.InvalidLoginException;
import com.disney.studios.user.exception.NotAValidEmailException;
import com.disney.studios.user.exception.UserAlreadyRegisteredException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@MockBean
	private UserService fakeUserService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldLogin() throws Exception {
		//given
		String email = "1234@344";
		String password = "secret";
		String token = "valid-token";
		when(this.fakeUserService.login(email, password)).thenReturn(token);

		//when
		MvcResult result = this.mockMvc.perform(
				post("/login")
						.param("email", email)
						.param("password", password)
		)
				.andExpect(status().isOk())
				.andReturn();

		//then
		assertThat(result.getResponse().getContentAsString()).isEqualTo(token);
	}

	@Test
	public void shouldRegister() throws Exception {
		//given
		String email = "1234@344";
		String password = "secret";
		String token = "valid-token";
		when(this.fakeUserService.register(email, password)).thenReturn(token);

		//when
		MvcResult result = this.mockMvc.perform(
				post("/register")
						.param("email", email)
						.param("password", password)
		)
				.andExpect(status().isOk())
				.andReturn();

		//then
		assertThat(result.getResponse().getContentAsString()).isEqualTo(token);
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
