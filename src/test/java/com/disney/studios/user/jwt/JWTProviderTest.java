package com.disney.studios.user.jwt;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.disney.studios.InstantService;
import com.disney.studios.user.User;
import com.disney.studios.user.UserToken;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Date;

import static com.google.common.truth.Truth.assertThat;
import static java.time.temporal.ChronoUnit.HOURS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JWTProviderTest {
	private JWTProvider jwtProvider;
	private InstantService fakeInstantService;
	private Instant instant;

	@Before
	public void setUp() throws NoSuchAlgorithmException {
		this.fakeInstantService = mock(InstantService.class);
		this.jwtProvider = new JWTProvider(fakeInstantService);
		this.instant = Instant.ofEpochMilli(10000000);
		when(this.fakeInstantService.getInstantNow()).thenReturn(this.instant);
	}

	@Test
	public void knowsAValidJWT(){
		//given
		UserToken token = jwtProvider.constructJWT(new User("email@email.com", ""));

		//when
		boolean response = this.jwtProvider.isValid(token.getToken());

		//then
		assertThat(response).isEqualTo(true);
	}


	@Test(expected = JWTDecodeException.class)
	public void showsAnInvalidJWT(){
		//given
		String token = "not-valid";

		//when
		boolean response = this.jwtProvider.isValid(token);

		//then
		assertThat(response).isEqualTo(true);
	}

	@Test
	public void showsABadSignatureJWT(){
		//given
		String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

		//when
		boolean response = this.jwtProvider.isValid(token);

		//then
		assertThat(response).isEqualTo(false);
	}

	@Test
	public void getEmailFromJWT(){
		//given
		String email = "email@email.com";
		UserToken token = this.jwtProvider.constructJWT(new User(email, ""));

		//when
		String response = this.jwtProvider.getEmail(token.getToken());

		//then
		assertThat(response).isEqualTo(email);
	}

	@Test
	public void getUserIDFromJWT(){
		//given
		Integer id = 10;
		UserToken token = this.jwtProvider.constructJWT(new User(id, "", ""));

		//when
		Integer response = this.jwtProvider.getUserId(token.getToken());

		//then
		assertThat(response).isEqualTo(id);
	}

	@Test
	public void constructJWT(){
		//given
		Integer id = 22;
		String email = "me@me.com";
		String hashedPassword = "hashed-secret";
		Date expectedExpirationDate = new Date(this.instant.plus(1, HOURS).toEpochMilli());

		//when
		UserToken userToken = this.jwtProvider.constructJWT(new User(id, email, hashedPassword));

		//then
		assertThat(this.jwtProvider.getEmail(userToken.getToken())).isEqualTo(email);
		assertThat(this.jwtProvider.getUserId(userToken.getToken())).isEqualTo(id);
		assertThat(this.jwtProvider.getExpiration(userToken.getToken())).isEqualTo(expectedExpirationDate);
	}

	@Test
	public void getTokenFromHeader(){
		//given
		String token = "valid-token";
		String header = "Bearer "+token;

		//when
		String response = this.jwtProvider.getTokenFromHeader(header);

		//then
		assertThat(response).isEqualTo(token);
	}
}
