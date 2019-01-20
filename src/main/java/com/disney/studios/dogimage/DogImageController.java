package com.disney.studios.dogimage;

import com.disney.studios.dogimage.vote.exception.DogImageNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
public class DogImageController {
	private final DogImageService dogImageService;

	public DogImageController(DogImageService dogImageService) {
		this.dogImageService = dogImageService;
	}

	@RequestMapping(path = "/breed/dogs", method = RequestMethod.GET)
	public Map<String, Set<DogImageDTO>> getDogImagesGroupedByBreed() {
		return this.dogImageService.getAllDogImagesByBreed();
	}

	@RequestMapping(path = "/dogs", method = RequestMethod.GET)
	public Iterable<DogImageDTO> getDogImages(@RequestParam(value = "breed") String breed) {
		return this.dogImageService.getDogImagesByBreed(breed);
	}

	@RequestMapping(path = "/dogs/{id}", method = RequestMethod.GET)
	public DogImageDTO getDogImages(@PathVariable Integer id) {
		Optional<DogImageDTO> dogImageDTO = this.dogImageService.getDogImage(id);
		if(dogImageDTO.isPresent()){
			return dogImageDTO.get();
		}else{
			throw new DogImageNotFoundException("Dog Image is Not Found");
		}
	}
}
