package com.disney.studios.dogimage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DogImageDTOServiceTest {

	@Test
	public void getDog() throws MalformedURLException {
		//given
		Integer id = 1;
		URL url = new URL("http://localhost");
		String breed = "Golden Retriever";
		Long voteCount = 19L;

		DogImageDTO expectedDogImage = new DogImageDTO(
				id,
				url,
				breed,
				voteCount
		);
		DogImageRepository fakeDogImageRepository = mock(DogImageRepository.class);

		DogImageDTOService dogImageDTOService = new DogImageDTOService(fakeDogImageRepository);

		when(fakeDogImageRepository.findDTOById(id)).thenReturn(Optional.of(expectedDogImage));

		//when
		Optional<DogImageDTO> response = dogImageDTOService.getDogImage(id);

		//then
		assertThat(response.get()).isEqualTo(expectedDogImage);
	}
}
