package com.disney.studios.dogimage;

import com.disney.studios.dogimage.vote.exception.DogImageNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class DogImageService {
	private final DogImageRepository dogImageRepository;

	public void save(URL url, String breed) {
		this.dogImageRepository.save(new DogImage(url, breed));
	}

	public Map<String, Set<DogImageDTO>> getAllDogImagesByBreed() {
		Iterable<DogImageDTO> dogImages = this.dogImageRepository.findAllDTO();

		return StreamSupport.stream(dogImages.spliterator(), true)
				.collect(Collectors.groupingBy(
						DogImageDTO::getBreed,
						Collectors.mapping(Function.identity(), Collectors.toSet())
				));
	}

	public Iterable<DogImageDTO> getDogImagesByBreed(String breed) {
		return this.dogImageRepository.findAllDTOByBreed(breed);
	}

	public DogImageDTO getDogImage(Integer imageId) {
		Optional<DogImageDTO> dogImageDTO = this.getDogImageOptional(imageId);
		if(dogImageDTO.isPresent()){
			return dogImageDTO.get();
		}else{
			throw new DogImageNotFoundException("Dog Image is Not Found");
		}
	}

	public Optional<DogImageDTO> getDogImageOptional(Integer imageId) {
		return this.dogImageRepository.findDTOById(imageId);
	}
}
