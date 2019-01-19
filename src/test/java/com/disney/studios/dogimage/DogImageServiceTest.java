package com.disney.studios.dogimage;

import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

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
	DogImageRepository fakeDogImageRepository;
	DogImageService dogImageService;

	@Before
	public void setUp(){
		fakeDogImageRepository = mock(DogImageRepository.class);
		dogImageService = new DogImageService(fakeDogImageRepository);
	}


	@Test
	public void shouldUseRepositoryToSaveDogEntityFromURL() throws MalformedURLException {
		//given
		URL url = new URL("http://www.google.com");
		String dogBreed = "Breed 1";
		DogImage expectDog = new DogImage(url, dogBreed);

		//when
		dogImageService.save(url, dogBreed);

		//then
		verify(fakeDogImageRepository, times(1)).save(expectDog);
	}

	@Test
	public void shouldGetAllDogImagesByBreed() throws MalformedURLException {
		//given
		List<DogImage> dogImagesInDatabase = Arrays.asList(
				getTestDogImage("http://www.google.com","Breed 1"),
				getTestDogImage("http://www.yahoo.com","Breed 2")
		);
		Map<String, List<URL>> expectedDogImages = ImmutableMap.<String, List<URL>>builder()
			.put(
				dogImagesInDatabase.get(0).getBreed(), Collections.singletonList(dogImagesInDatabase.get(0).getUrl())
			)
			.put(
				dogImagesInDatabase.get(1).getBreed(), Collections.singletonList(dogImagesInDatabase.get(1).getUrl())
			)
			.build();

		when(fakeDogImageRepository.findAll()).thenReturn(dogImagesInDatabase);

		//when
		Map<String, List<URL>> returnedDogImages = dogImageService.getAllDogImagesByBreed();

		//then
		assertThat(returnedDogImages).isEqualTo(expectedDogImages);
	}

	@Test
	public void shouldGetDogsOfAParticularBreed() throws MalformedURLException {
		//given
		List<DogImage> dogImages = Arrays.asList(getTestDogImage("http://google.com", "Breed 1"));
		List<URL> expectedDogImages =  Collections.singletonList(dogImages.get(0).getUrl());
		when(fakeDogImageRepository.findAllByBreed(dogImages.get(0).getBreed())).thenReturn(dogImages);

		//when
		List<URL> returnedDogImages = dogImageService.getDogImagesByBreed(dogImages.get(0).getBreed());

		//then
		assertThat(returnedDogImages).isEqualTo(expectedDogImages);
	}

	private DogImage getTestDogImage(String url, String breed) throws MalformedURLException {
		return new DogImage(new URL(url), breed);
	}
}
