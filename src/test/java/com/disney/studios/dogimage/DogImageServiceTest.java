package com.disney.studios.dogimage;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.repository.CrudRepository;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DogImageServiceTest {

	@Test
	public void shouldUseRepositoryToSaveDogEntityFromURL() throws MalformedURLException {
		//given
		URL url = new URL("http://www.google.com");
		DogBreed dogBreed = new DogBreed("Breed 1");
		DogImage expectDog = new DogImage(url, dogBreed);
		CrudRepository<DogImage, Integer> fakeDogImageRepository = mock(CrudRepository.class);
		CrudRepository<DogBreed, String> fakeDogBreedRepository = mock(CrudRepository.class);
		DogImageService dogImageService = new DogImageService(fakeDogImageRepository, fakeDogBreedRepository);

		//when
		dogImageService.save(url, dogBreed.getName());

		//then
		verify(fakeDogImageRepository, times(1)).save(expectDog);
		verify(fakeDogImageRepository, times(1)).save(expectDog);
	}

	@Test
	public void shouldGetAllDogImagesByBreed() throws MalformedURLException {
		//given
		List<DogImage> dogImagesInDatabase = Arrays.asList(
				new DogImage(new URL("http://www.google.com"), new DogBreed("Breed 1")),
				new DogImage(new URL("http://www.yahoo.com"), new DogBreed("Breed 2"))
		);
		Map<String, List<URL>> expectedDogImages = ImmutableMap.<String, List<URL>>builder()
			.put(
				dogImagesInDatabase.get(0).getBreed().getName(), Collections.singletonList(dogImagesInDatabase.get(0).getUrl())
			)
			.put(
				dogImagesInDatabase.get(1).getBreed().getName(), Collections.singletonList(dogImagesInDatabase.get(1).getUrl())
			)
			.build();

		CrudRepository<DogImage, Integer> fakeDogImageRepository = mock(CrudRepository.class);
		CrudRepository<DogBreed, String> fakeDogBreedRepository = mock(CrudRepository.class);
		when(fakeDogImageRepository.findAll()).thenReturn(dogImagesInDatabase);

		DogImageService dogImageService = new DogImageService(fakeDogImageRepository, fakeDogBreedRepository);

		//when
		Map<String, List<URL>> returnedDogImages = dogImageService.getAllDogImagesByBreed();

		//then
		assertThat(returnedDogImages).isEqualTo(expectedDogImages);
	}
}
