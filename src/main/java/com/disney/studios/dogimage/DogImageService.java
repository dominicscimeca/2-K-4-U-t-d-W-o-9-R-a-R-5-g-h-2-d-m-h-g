package com.disney.studios.dogimage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import java.net.URL;

public class DogImageService {
	private final CrudRepository<DogImageDao, Integer> dogImageRepository;

	public DogImageService(@Autowired CrudRepository<DogImageDao, Integer> dogImageRepository) {
		this.dogImageRepository = dogImageRepository;
	}

	public void save(URL url) {
		this.save(new DogImageDao(url));
	}

	protected void save(DogImageDao dog) {
		this.dogImageRepository.save(dog);
	}
}
