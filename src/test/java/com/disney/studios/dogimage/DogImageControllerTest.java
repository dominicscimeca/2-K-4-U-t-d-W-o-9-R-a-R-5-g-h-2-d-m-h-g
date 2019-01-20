package com.disney.studios.dogimage;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DogImageController.class)
public class DogImageControllerTest {

	@MockBean
	private DogImageService dogImageService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldAskDogImageServiceForImage() throws Exception {
		//given
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

		when(dogImageService.getAllDogImagesByBreed()).thenReturn(expectedMap);
		String expectedResponseAsString = "{\"Breed 1\":[{\"id\":1,\"url\":\"http://google.com\",\"breed\":\"Breed 1\",\"voteCount\":2},{\"id\":2,\"url\":\"http://yahoo.com\",\"breed\":\"Breed 1\",\"voteCount\":-2}],\"Breed 2\":[{\"id\":1,\"url\":\"http://hotmail.com\",\"breed\":\"Breed 2\",\"voteCount\":4},{\"id\":2,\"url\":\"http://dogs.com\",\"breed\":\"Breed 2\",\"voteCount\":-1}]}";

		//when
		MvcResult result = this.mockMvc.perform(get("/breed/dogs"))
				.andExpect(status().isOk())
				.andReturn();

		//then
		String responseAsAString = result.getResponse().getContentAsString();

		assertThat(responseAsAString).isEqualTo(expectedResponseAsString);
	}

	@Test
	public void shouldAskDogImageServiceForParticularBreed() throws Exception {
		//given
		String breed = "Breed 1";
		String url1 = "http://yahoo.com";
		String url2 = "http://google.com";
		Iterable<DogImageDTO> expectedImages = Arrays.asList(
				new DogImageDTO(1, new URL(url1), breed, 2L),
				new DogImageDTO(2, new URL(url2), breed, -2L)
		);

		when(dogImageService.getDogImagesByBreed(breed)).thenReturn(expectedImages);
		String expectedResponseAsString = "[{\"id\":1,\"url\":\"http://yahoo.com\",\"breed\":\"Breed 1\",\"voteCount\":2},{\"id\":2,\"url\":\"http://google.com\",\"breed\":\"Breed 1\",\"voteCount\":-2}]";

		//when
		MvcResult result = this.mockMvc.perform(get("/dogs?breed={breed}", breed))
				.andExpect(status().isOk())
				.andReturn();

		//then
		String responseAsAString = result.getResponse().getContentAsString();

		assertThat(responseAsAString).isEqualTo(expectedResponseAsString);
	}

	@Test
	public void getDog() throws Exception {
		//given
		Integer id = 9;
		DogImageDTO dogImageDTO = new DogImageDTO(9, new URL("http://google.com"), "Breed 1", 2L);


		when(dogImageService.getDogImage(id)).thenReturn(Optional.of(dogImageDTO));
		String expectedResponseAsString = "{\"id\":9,\"url\":\"http://google.com\",\"breed\":\"Breed 1\",\"voteCount\":2}";

		//when
		MvcResult result = this.mockMvc.perform(get("/dogs/{id}", id))
				.andExpect(status().isOk())
				.andReturn();

		//then
		String responseAsAString = result.getResponse().getContentAsString();

		assertThat(responseAsAString).isEqualTo(expectedResponseAsString);
	}

	@Test
	public void getDogNotFound() throws Exception {
		//given
		Integer id = 9;
		when(dogImageService.getDogImage(id)).thenReturn(Optional.empty());

		//when
		this.mockMvc.perform(get("/dogs/{id}", id))
				.andExpect(status().isNotFound());
	}
}
