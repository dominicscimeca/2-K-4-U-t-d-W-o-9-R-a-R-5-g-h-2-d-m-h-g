package com.disney.studios.user;

import com.disney.studios.dogimage.vote.UnauthorizedException;
import com.disney.studios.dogimage.vote.UserNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.MalformedURLException;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	private UserService userService;
	private UserRepository fakeUserRepository;
	private JWTProvider fakeJWTProvider;
	private String email;
	private User user;
	private String token;

	@Before
	public void setUp(){
		this.fakeUserRepository = mock(UserRepository.class);
		this.fakeJWTProvider = mock(JWTProvider.class);
		this.userService = new UserService(this.fakeUserRepository, this.fakeJWTProvider);
		this.email = "email@email.com";
		this.token = "valid-token";
		this.user = new User(email, "");

		when(this.fakeJWTProvider.constructJWT(any())).thenReturn(token);
	}

	@Test
	public void shouldRegisterIfNew(){
		//given
		String password = "secretsecret";

		//when
		String token = this.userService.register(email, password);

		//then
		assertThat(token).isEqualTo(this.token);
	}

	@Test(expected = NotAValidEmailException.class)
	public void shouldThrowEmailInvalidIfNotAValidUser(){
		this.userService.register("not-valid", "");
	}

	@Test(expected = UserAlreadyRegisteredException.class)
	public void shouldThrowUserAlreadyRegisteredExceptionIfNotNew(){
		//given
		String email = "email@email.com";
		User user = new User(email);
		when(this.fakeUserRepository.getUserByEmail(email)).thenReturn(user);

		//when
		this.userService.register(email, "secret");

		//then
	}

	@Test
	public void shouldReturnJWTIfLoginPasses(){
		//given
		String email = "email@email.com";
		String password = "secret";
		when(this.fakeUserRepository.getUserByEmailAndHashedPassword(eq(email), any())).thenReturn(user);

		//when
		String token = this.userService.login(email, password);

		//then
		assertThat(token).isEqualTo(this.token);
	}

	@Test(expected = InvalidLoginException.class)
	public void shouldThrowIncorrectLoginException(){
		//given
		String email = "email@email.com";
		String password = "secret";
		when(this.fakeUserRepository.getUserByEmailAndHashedPassword(eq(email), any())).thenReturn(null);

		//when
		this.userService.login(email, password);
	}

	@Test
	public void shouldGetUserByEmail(){
		//given
		String email = "email@email.com";
		when(this.fakeUserRepository.getUserByEmail(email)).thenReturn(user);

		//when
		User user = this.userService.getUserByEmail(email);

		//then
		assertThat(user).isEqualTo(this.user);
	}

	@Test(expected = UnauthorizedException.class)
	public void shouldUseVoteServiceToVoteUpNotAuthorized() throws MalformedURLException {
		//given
		when(this.fakeJWTProvider.isValid(any())).thenReturn(false);

		//when
		this.userService.getUserFromToken("");

	}

	@Test(expected = UserNotFoundException.class)
	public void shouldUseVoteServiceToVoteDownNotAuthorized() throws MalformedURLException {
		//given
		when(this.fakeJWTProvider.isValid(any())).thenReturn(true);
		when(this.fakeUserRepository.getUserByEmail(any())).thenReturn(null);

		//when
		this.userService.getUserFromToken(null);

		//then
	}
}
