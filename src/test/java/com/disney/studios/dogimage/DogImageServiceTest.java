package com.disney.studios.dogimage;

import com.google.common.collect.ImmutableMap;
import org.junit.Before;
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
	DogImageRepository fakeDogImageRepository;
	DogBreedRepository fakeDogBreedRepository;
	DogImageService dogImageService;

	@Before
	public void setUp(){
		fakeDogImageRepository = mock(DogImageRepository.class);
		fakeDogBreedRepository = mock(DogBreedRepository.class);
		dogImageService = new DogImageService(fakeDogImageRepository, fakeDogBreedRepository);
	}


	@Test
	public void shouldUseRepositoryToSaveDogEntityFromURL() throws MalformedURLException {
		//given
		URL url = new URL("http://www.google.com");
		DogBreed dogBreed = new DogBreed("Breed 1");
		DogImage expectDog = new DogImage(url, dogBreed);
		when(fakeDogBreedRepository.save(dogBreed)).thenReturn(dogBreed);

		//when
		dogImageService.save(url, dogBreed.getName());

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
				dogImagesInDatabase.get(0).getBreed().getName(), Collections.singletonList(dogImagesInDatabase.get(0).getUrl())
			)
			.put(
				dogImagesInDatabase.get(1).getBreed().getName(), Collections.singletonList(dogImagesInDatabase.get(1).getUrl())
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
		List<URL> returnedDogImages = dogImageService.getDogImagesByBreed(dogImages.get(0).getBreed().getName());

		//then
		assertThat(returnedDogImages).isEqualTo(expectedDogImages);
	}

	private DogImage getTestDogImage(String url, String breed) throws MalformedURLException {
		return new DogImage(new URL(url), new DogBreed(breed));
	}
}
