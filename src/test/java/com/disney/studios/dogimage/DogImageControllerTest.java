package com.disney.studios.dogimage;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DogImageControllerTest {

	@Test
	public void shouldAskDogImageServiceForImage() throws MalformedURLException {
		//given
		DogImageService fakeDogImageService = mock(DogImageService.class);
		Map<String, Set<DogImageDTO>> expectedMap = ImmutableMap.<String, Set<DogImageDTO>>builder()
				.put("Breed 1",  ImmutableSet.of(
					new DogImageDTO(1, new URL("http://google.com"), "Breed 1", 2L),
					new DogImageDTO(2, new URL("http://yahoo.com"), "Breed 1", -2L)
				))
				.put("Breed 2", ImmutableSet.of(
					new DogImageDTO(1, new URL("http://hotmail.com"), "Breed 2", 4L),
					new DogImageDTO(2, new URL("http://dogs.com"), "Breed 2", -1L)
				))
				.build();

		when(fakeDogImageService.getAllDogImagesByBreed()).thenReturn(expectedMap);
		DogImageController dogImageController = new DogImageController(fakeDogImageService);

		//when
		Map<String, Set<DogImageDTO>> returnedMap = dogImageController.getDogImagesGroupedByBreed();

		//then
		assertThat(returnedMap).isEqualTo(expectedMap);
	}

	@Test
	public void shouldAskDogImageServiceForParticularBreed() throws MalformedURLException {
		//given
		DogImageService fakeDogImageService = mock(DogImageService.class);
		String breed = "Breed 1";
		String url1 = "http://yahoo.com";
		String url2 = "http://google.com";
		Iterable<DogImageDTO> expectedImages = Arrays.asList(
				new DogImageDTO(1, new URL(url1), breed, 2L),
				new DogImageDTO(2, new URL(url2), breed, -2L)
		);

		when(fakeDogImageService.getDogImagesByBreed(breed)).thenReturn(expectedImages);
		DogImageController dogImageController = new DogImageController(fakeDogImageService);

		//when
		Iterable<DogImageDTO> returnedImages = dogImageController.getDogImages(breed);

		//then
		assertThat(returnedImages).isEqualTo(expectedImages);
	}
}
