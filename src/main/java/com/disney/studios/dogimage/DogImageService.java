package com.disney.studios.dogimage;

import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DogImageService {
	private final DogImageRepository dogImageRepository;

	public DogImageService(DogImageRepository dogImageRepository) {
		this.dogImageRepository = dogImageRepository;
	}

	public void save(URL url, String breed) {
		this.save(new DogImage(url, breed));
	}

	protected void save(DogImage dog) {
		this.dogImageRepository.save(dog);
	}

	public Map<String, List<URL>> getAllDogImagesByBreed() {
		Iterable<DogImage> dogImages = this.dogImageRepository.findAll();

		return StreamSupport.stream(dogImages.spliterator(), true)
				.collect(Collectors.groupingBy(
						DogImage::getBreed,
						Collectors.mapping(DogImage::getUrl, Collectors.toList())
				));
	}

	public List<URL> getDogImagesByBreed(String breed) {
		Iterable<DogImage> dogImages = this.dogImageRepository.findAllByBreed(breed);

		return StreamSupport.stream(dogImages.spliterator(), true)
				.map(DogImage::getUrl)
				.collect(Collectors.toList());
	}

	public DogImage getDogImageByURL(URL url) {
		return null;
	}

	public Optional<DogImage> getDogImage(Integer imageId) {
		return this.dogImageRepository.findById(imageId);
	}
}
