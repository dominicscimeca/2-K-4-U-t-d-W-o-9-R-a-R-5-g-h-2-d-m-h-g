package com.disney.studios.dogimage;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DogImageService {
	private final CrudRepository<DogImage, Integer> dogImageRepository;
	private final CrudRepository<DogBreed, String> dogBreedRepository;

	public DogImageService(
			CrudRepository<DogImage, Integer> dogImageRepository,
			CrudRepository<DogBreed, String> dogBreedRepository
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

		return StreamSupport.stream(dogImages.spliterator(), false)
				.collect(Collectors.groupingBy(
						dogImage -> dogImage.getBreed().getName(),
						Collectors.mapping(DogImage::getUrl, Collectors.toList())
				));
	}
}
