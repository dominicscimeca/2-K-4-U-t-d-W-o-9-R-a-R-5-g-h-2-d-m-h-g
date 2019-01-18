package com.disney.studios.dogimage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DogImageServiceTest {

	@Test
	public void shouldUseRepositoryToSaveDogEntity(){
		//given
		Repository fakeDogImageRepository = mock(Repository.class);
		DogImageService dogImageService = new DogImageService(fakeDogImageRepository);

		//when
		dogImageService.save(dog);

		//then
		verify(fakeDogImageRepository, times(1)).save(dog);
	}
}
