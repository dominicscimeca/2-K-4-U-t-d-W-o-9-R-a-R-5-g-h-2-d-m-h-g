package com.disney.studios.dogimage;

import org.springframework.data.repository.CrudRepository;

public interface DogImageRepository extends CrudRepository<DogImage, Integer> {
	Iterable<DogImage> findAllByBreed(DogBreed breed);
}
