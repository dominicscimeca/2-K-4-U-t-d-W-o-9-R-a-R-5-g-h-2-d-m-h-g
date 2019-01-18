package com.disney.studios.dogimage;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DogImageControllerTest {

	@Test
	public void shouldAskDogImageServiceForImage() throws MalformedURLException {
		//given
		DogImageService fakeDogImageService = mock(DogImageService.class);
		Map<String, List<URL>> expectedMap = ImmutableMap.<String, List<URL>>builder()
				.put("Breed 1", Arrays.asList(new URL("http://yahoo.com"), new URL("http://google.com")))
				.put("Breed 2", Arrays.asList(new URL("http://hotmail.com"), new URL("http://dogs.com")))
				.build();

		when(fakeDogImageService.getAllDogImagesByBreed()).thenReturn(expectedMap);
		DogImageController dogImageController = new DogImageController(fakeDogImageService);

		//when
		Map<String, List<URL>> returnedMap = dogImageController.getDogImagesGroupedByBreed();

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
		List<URL> expectedImages = Arrays.asList(new URL(url1), new URL(url2));


		when(fakeDogImageService.getDogImagesByBreed(breed)).thenReturn(expectedImages);
		DogImageController dogImageController = new DogImageController(fakeDogImageService);

		//when
		List<URL> returnedImages = dogImageController.getDogImages(breed);

		//then
		assertThat(returnedImages).isEqualTo(expectedImages);
	}
}
