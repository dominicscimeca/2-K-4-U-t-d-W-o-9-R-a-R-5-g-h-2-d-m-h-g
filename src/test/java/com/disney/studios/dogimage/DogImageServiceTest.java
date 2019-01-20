package com.disney.studios.dogimage;

import com.disney.studios.dogimage.vote.exception.DogImageNotFoundException;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DogImageServiceTest {
	private DogImageRepository fakeDogImageRepository;
	private DogImageService dogImageService;

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
	public void shouldGetAllDogImagesByBreed() {
		//given
		List<DogImageDTO> dogImagesInDatabase = Arrays.asList(
				getTestDogImageDTO("http://www.google.com","Breed 1"),
				getTestDogImageDTO("http://www.yahoo.com","Breed 2")
		);
		Map<String, Set<DogImageDTO>> expectedDogImages = ImmutableMap.<String, Set<DogImageDTO>>builder()
			.put(
				dogImagesInDatabase.get(0).getBreed(), Collections.singleton(dogImagesInDatabase.get(0))
			)
			.put(
				dogImagesInDatabase.get(1).getBreed(), Collections.singleton(dogImagesInDatabase.get(1))
			)
			.build();

		when(fakeDogImageRepository.findAllDTO()).thenReturn(dogImagesInDatabase);

		//when
		Map<String, Set<DogImageDTO>> returnedDogImages = dogImageService.getAllDogImagesByBreed();

		//then
		assertThat(returnedDogImages).isEqualTo(expectedDogImages);
	}

	@Test
	public void shouldGetDogsOfAParticularBreed() {
		//given
		String breed = "German Shepard";

		Iterable<DogImageDTO> dogImages = Collections.singletonList(getTestDogImageDTO("http://google.com", breed));
		when(fakeDogImageRepository.findAllDTOByBreed(breed)).thenReturn(dogImages);

		//when
		Iterable<DogImageDTO> returnedDogImages = dogImageService.getDogImagesByBreed(breed);

		//then
		assertThat(returnedDogImages).isEqualTo(dogImages);
	}

	@Test
	public void getDogOptional() {
		//given
		Integer id = 1;
		DogImageDTO expectedDogImage = new DogImageDTO(id, "http://localhost", "Golden Retriever", 19L);

		when(fakeDogImageRepository.findDTOById(id)).thenReturn(Optional.of(expectedDogImage));

		//when
		Optional<DogImageDTO> response = dogImageService.getDogImageOptional(id);

		//then
		assertThat(response.get()).isEqualTo(expectedDogImage);
	}

	@Test
	public void getDog() {
		//given
		Integer id = 1;
		DogImageDTO expectedDogImage = new DogImageDTO(id, "http://localhost", "Golden Retriever", 19L);

		when(fakeDogImageRepository.findDTOById(id)).thenReturn(Optional.of(expectedDogImage));

		//when
		DogImageDTO response = dogImageService.getDogImage(id);

		//then
		assertThat(response).isEqualTo(expectedDogImage);
	}

	@Test(expected = DogImageNotFoundException.class)
	public void getDogNotFound() {
		//given
		Integer id = 1;

		when(fakeDogImageRepository.findDTOById(id)).thenReturn(Optional.empty());

		//when
		dogImageService.getDogImage(id);
	}

	private DogImageDTO getTestDogImageDTO(String url, String breed) {
		return new DogImageDTO(1, url, breed, 1L);
	}
}
