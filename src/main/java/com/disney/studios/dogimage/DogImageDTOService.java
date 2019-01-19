package com.disney.studios.dogimage;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class DogImageDTOService {
	private final DogImageRepository dogImageRepository;

	public Optional<DogImageDTO> getDogImage(Integer id) {
		return this.dogImageRepository.findDTOById(id);
	}
}
