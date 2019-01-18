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
		Map<String, List<URL>> returnedMap = dogImageController.getDogImagesByBreed();

		//then
		assertThat(returnedMap).isEqualTo(expectedMap);
	}
}
