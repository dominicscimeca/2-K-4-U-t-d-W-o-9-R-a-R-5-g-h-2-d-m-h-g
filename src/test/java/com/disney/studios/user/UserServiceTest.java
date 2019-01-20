package com.disney.studios.user;

import com.disney.studios.dogimage.vote.exception.UnauthorizedException;
import com.disney.studios.user.exception.InvalidLoginException;
import com.disney.studios.user.exception.NotAValidEmailException;
import com.disney.studios.user.exception.UserAlreadyRegisteredException;
import com.disney.studios.user.exception.UserNotFoundException;
import com.disney.studios.user.jwt.JWTProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

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
	private UserToken userToken;

	@Before
	public void setUp(){
		this.fakeUserRepository = mock(UserRepository.class);
		this.fakeJWTProvider = mock(JWTProvider.class);
		this.userService = new UserService(this.fakeUserRepository, this.fakeJWTProvider);
		this.email = "email@email.com";
		this.token = "valid-token";
		this.userToken = new UserToken(this.email, this.token, new Date());
		this.user = new User(email, "secrethashed");

		when(this.fakeUserRepository.getUserByEmail(this.email)).thenReturn(this.user);
		when(this.fakeJWTProvider.constructJWT(this.user)).thenReturn(userToken);
	}

	@Test
	public void shouldRegisterIfNew(){
		//given
		when(this.fakeUserRepository.getUserByEmail(this.email)).thenReturn(null);
		when(this.fakeUserRepository.save(any())).thenReturn(this.user);

		//when
		UserToken token = this.userService.register(email, "secret");

		//then
		assertThat(token).isEqualTo(this.userToken);
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
		UserToken token = this.userService.login(email, password);

		//then
		assertThat(token.getToken()).isEqualTo(this.token);
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

	@Test
	public void shouldGetUserByAuthHeader(){
		//given
		String authHeader = "valid-auth-header";
		String email = "email@email.com";
		when(this.fakeJWTProvider.getTokenFromHeader(authHeader)).thenReturn(token);
		when(this.fakeJWTProvider.getEmail(token)).thenReturn(email);
		when(this.fakeUserRepository.getUserByEmail(email)).thenReturn(user);

		//when
		User user = this.userService.getUserByAuthHeader(authHeader);

		//then
		assertThat(user).isEqualTo(this.user);
	}

	@Test(expected = UnauthorizedException.class)
	public void shouldUseVoteServiceToVoteUpNotAuthorized() {
		//given
		when(this.fakeJWTProvider.isValid(any())).thenReturn(false);

		//when
		this.userService.getUserFromToken("");
	}

	@Test(expected = UserNotFoundException.class)
	public void shouldUseVoteServiceToVoteDownNotAuthorized() {
		//given
		when(this.fakeJWTProvider.isValid(any())).thenReturn(true);
		when(this.fakeUserRepository.getUserByEmail(any())).thenReturn(null);

		//when
		this.userService.getUserFromToken(null);
	}
}
