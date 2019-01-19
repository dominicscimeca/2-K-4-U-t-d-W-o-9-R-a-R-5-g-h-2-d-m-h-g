package com.disney.studios.dogimage;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DogImageRepository extends CrudRepository<DogImage, Integer> {
	@Query("select new com.disney.studios.dogimage.DogImageDTO(di.id, di.url, di.breed, COALESCE(sum(v.vote),0)) from DogImage di left join com.disney.studios.dogimage.vote.Vote v on di.id = v.dog group by di.id order by COALESCE(sum(v.vote),0) desc")
	Iterable<DogImageDTO> findAllDTO();

	@Query("select new com.disney.studios.dogimage.DogImageDTO(di.id, di.url, di.breed, COALESCE(sum(v.vote),0)) from DogImage di left join com.disney.studios.dogimage.vote.Vote v on di.id = v.dog where di.breed = :breed group by di.id order by COALESCE(sum(v.vote),0) desc")
	Iterable<DogImageDTO> findAllDTOByBreed(String breed);

	@Query("select new com.disney.studios.dogimage.DogImageDTO(di.id, di.url, di.breed, COALESCE(sum(v.vote),0)) from DogImage di left join com.disney.studios.dogimage.vote.Vote v on di.id = v.dog where di.id = :id group by di.id")
	Optional<DogImageDTO> findDTOById(Integer id);
}
