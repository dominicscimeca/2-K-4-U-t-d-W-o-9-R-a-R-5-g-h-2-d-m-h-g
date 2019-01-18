package com.disney.studios.dogimage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.repository.CrudRepository;

import java.net.MalformedURLException;
import java.net.URL;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DogImageServiceTest {

	@Test
	public void shouldUseRepositoryToSaveDogEntityFromURL() throws MalformedURLException {
		//given
		URL url = new URL("http://www.google.com");
		DogImageDao expectDog = new DogImageDao(url);
		CrudRepository<DogImageDao, Integer> fakeDogImageRepository = mock(CrudRepository.class);
		DogImageService dogImageService = new DogImageService(fakeDogImageRepository);

		//when
		dogImageService.save(url);

		//then
		verify(fakeDogImageRepository, times(1)).save(expectDog);
	}
}
