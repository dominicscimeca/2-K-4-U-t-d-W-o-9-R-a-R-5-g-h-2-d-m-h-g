package com.disney.studios.dogimage;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.util.List;
import java.util.Map;

@RestController
public class DogImageController {
	private final DogImageService dogImageService;

	public DogImageController(DogImageService dogImageService) {
		this.dogImageService = dogImageService;
	}

	@RequestMapping(path = "/dogsByBreed", method = RequestMethod.GET)
	public Map<String, List<URL>> getDogImagesGroupedByBreed() {
		return this.dogImageService.getAllDogImagesByBreed();
	}

	@RequestMapping(path = "/dogs", method = RequestMethod.GET)
	public List<URL> getDogImages(@RequestParam(value = "breed") String breed) {
		return this.dogImageService.getDogImagesByBreed(breed);
	}
}
