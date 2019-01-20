package com.disney.studios.user.jwt;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.disney.studios.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.security.NoSuchAlgorithmException;

import static com.google.common.truth.Truth.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class JWTProviderTest {
	private JWTProvider jwtProvider;

	@Before
	public void setUp() throws NoSuchAlgorithmException {
		this.jwtProvider = new JWTProvider();
	}

	@Test
	public void knowsAValidJWT(){
		//given
		String token = jwtProvider.constructJWT(new User("email@email.com", ""));

		//when
		boolean response = this.jwtProvider.isValid(token);

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
		String token = this.jwtProvider.constructJWT(new User(email, ""));

		//when
		String response = this.jwtProvider.getEmail(token);

		//then
		assertThat(response).isEqualTo(email);
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
