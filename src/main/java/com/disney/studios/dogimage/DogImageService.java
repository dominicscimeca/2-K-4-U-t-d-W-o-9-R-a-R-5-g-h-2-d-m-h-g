package com.disney.studios.dogimage;

import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DogImageService {
	private final DogImageRepository dogImageRepository;
	private final DogBreedRepository dogBreedRepository;

	public DogImageService(
			DogImageRepository dogImageRepository,
			DogBreedRepository dogBreedRepository
	) {
		this.dogImageRepository = dogImageRepository;
		this.dogBreedRepository = dogBreedRepository;
	}

	public void save(URL url, String breed) {
		DogBreed dogBreed = new DogBreed(breed);
		DogBreed returnedDogBreed = this.dogBreedRepository.save(dogBreed);

		this.save(new DogImage(url, returnedDogBreed));
	}

	protected void save(DogImage dog) {
		this.dogImageRepository.save(dog);
	}

	public Map<String, List<URL>> getAllDogImagesByBreed() {
		Iterable<DogImage> dogImages = this.dogImageRepository.findAll();

		return StreamSupport.stream(dogImages.spliterator(), true)
				.collect(Collectors.groupingBy(
						dogImage -> dogImage.getBreed().getName(),
						Collectors.mapping(DogImage::getUrl, Collectors.toList())
				));
	}

	public List<URL> getDogImagesByBreed(String breed) {
		Iterable<DogImage> dogImages = this.dogImageRepository.findAllByBreed(new DogBreed(breed));

		return StreamSupport.stream(dogImages.spliterator(), true)
				.map(DogImage::getUrl)
				.collect(Collectors.toList());
	}
}
