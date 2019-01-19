package com.disney.studios.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
	private UserController userController;
	private UserService fakeUserService;

	@Before
	public void setUp(){
		this.fakeUserService = mock(UserService.class) ;
		this.userController = new UserController(fakeUserService);
	}


	@Test
	public void shouldLogin(){
		//given
		String email = "1234@344";
		String password = "secret";
		String token = "valid-token";
		when(this.fakeUserService.login(email, password)).thenReturn(token);

		//when
		String response = this.userController.login(email, password);

		//then
		assertThat(response).isEqualTo(token);
	}

	@Test
	public void shouldRegister(){
		//given
		String email = "1234@344";
		String password = "secret";
		String token = "valid-token";
		when(this.fakeUserService.register(email, password)).thenReturn(token);

		//when
		String response = this.userController.register(email, password);

		//then
		assertThat(response).isEqualTo(token);
	}
}
