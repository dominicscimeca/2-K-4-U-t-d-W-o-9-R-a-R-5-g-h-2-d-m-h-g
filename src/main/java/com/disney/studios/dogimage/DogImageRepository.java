package com.disney.studios.dogimage;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DogImageRepository extends CrudRepository<DogImage, Integer> {
	Iterable<DogImage> findAllByBreed(String breed);

	@Query("select new com.disney.studios.dogimage.DogImageDTO(di.id, di.url, di.breed, sum(v.vote)) from DogImage di, com.disney.studios.dogimage.vote.Vote v where di.id = :id and v.dog = di.id")
	Optional<DogImageDTO> findDTOById(Integer id);
}
