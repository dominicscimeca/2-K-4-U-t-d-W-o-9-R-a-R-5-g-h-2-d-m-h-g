package com.disney.studios.dogimage.vote;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VoteRepository extends CrudRepository<Vote, Integer> {
	Optional<Vote> findByDogAndUser(Integer dog, Integer user);
}
