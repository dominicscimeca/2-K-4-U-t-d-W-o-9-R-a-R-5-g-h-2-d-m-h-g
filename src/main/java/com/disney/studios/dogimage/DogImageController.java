package com.disney.studios.dogimage;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@AllArgsConstructor
public class DogImageController {
	private final DogImageService dogImageService;

	@ApiOperation(
			value = "Find images of dogs grouped by breed",
			notes = "Returns a map of dogs images with the keys being breeds and the values a set of dog images sorted by vote count desc"
	)
	@RequestMapping(path = "/breed/dogs", method = RequestMethod.GET)
	public Map<String, Set<DogImageDTO>> getDogImagesGroupedByBreed() {
		return this.dogImageService.getAllDogImagesByBreed();
	}

	@ApiOperation(
			value = "Find images of dogs by breed",
			notes = "Returns a list of dogs images filtered by breed and sorted by vote count desc"
	)
	@RequestMapping(path = "/dogs", method = RequestMethod.GET)
	public Iterable<DogImageDTO> getDogImages(@RequestParam(value = "breed") String breed) {
		return this.dogImageService.getDogImagesByBreed(breed);
	}

	@ApiOperation(
			value = "Get a specific image of a dog by id",
			notes = "Returns the image of the dog, found by id, including vote count"
	)
	@RequestMapping(path = "/dogs/{id}", method = RequestMethod.GET)
	public DogImageDTO getDogImages(@PathVariable Integer id) {
		return this.dogImageService.getDogImage(id);
	}
}
