package com.disney.studios.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.google.common.truth.Truth.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class JWTProviderTest {
	private JWTProvider jwtProvider;

	@Before
	public void setUp(){
		this.jwtProvider = new JWTProvider();
	}

	@Test
	public void knowsAValidJWT(){
		//given
		DecodedJWT jwt = JWT.decode("jwtString");

		//when
		boolean response = this.jwtProvider.isValid(jwt);

		//then
		assertThat(response).isEqualTo(true);
	}

	@Test
	public void showsAnInvalidJWT(){
		//given
		DecodedJWT jwt = JWT.decode("jwtString");

		//when
		boolean response = this.jwtProvider.isValid(jwt);

		//then
		assertThat(response).isEqualTo(true);
	}
}
