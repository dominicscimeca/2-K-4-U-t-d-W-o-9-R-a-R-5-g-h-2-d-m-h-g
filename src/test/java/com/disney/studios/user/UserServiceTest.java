package com.disney.studios.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	private UserService userService;
	private UserRepository fakeUserRepository;
	private JWTProvider fakeJWTProvider;
	private String email;
	private User user;
	private DecodedJWT jwt;

	@Before
	public void setUp(){
		this.fakeUserRepository = mock(UserRepository.class);
		this.fakeJWTProvider = mock(JWTProvider.class);
		this.userService = new UserService(this.fakeUserRepository, this.fakeJWTProvider);
		this.email = "email@email.com";
		this.jwt = mock(DecodedJWT.class);
		this.user = new User(email, "");

		when(this.fakeJWTProvider.constructJWT(any())).thenReturn(jwt);
	}

	@Test
	public void shouldRegisterIfNew(){
		//given
		String password = "secretsecret";

		//when
		DecodedJWT jwt = this.userService.register(email, password);

		//then
		assertThat(jwt).isEqualTo(this.jwt);
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
		when(this.fakeUserRepository.getUserByEmailAndHashedPassword(email, any())).thenReturn(user);

		//when
		DecodedJWT jwt = this.userService.login(email, password);

		//then
		assertThat(jwt).isEqualTo(this.jwt);
	}

	@Test(expected = InvalidLoginException.class)
	public void shouldThrowIncorrectLoginException(){
		//given
		String email = "email@email.com";
		String password = "secret";
		when(this.fakeUserRepository.getUserByEmailAndHashedPassword(email, any())).thenReturn(null);

		//when
		this.userService.login(email, password);
	}
}
