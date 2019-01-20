package com.disney.studios.dogimage;

import com.disney.studios.dogimage.vote.exception.DogImageNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private final Logger log = LoggerFactory.getLogger(DogImageService.class);

	private final DogImageRepository dogImageRepository;

	public void save(URL url, String breed) {
		this.dogImageRepository.save(new DogImage(url, breed));
	}

	public Map<String, Set<DogImageDTO>> getAllDogImagesByBreed() {
		log.info("Getting all dogs grouped by breed");
		Iterable<DogImageDTO> dogImages = this.dogImageRepository.findAllDTO();

		return StreamSupport.stream(dogImages.spliterator(), true)
				.collect(Collectors.groupingBy(
						DogImageDTO::getBreed,
						Collectors.mapping(Function.identity(), Collectors.toSet())
				));
	}

	public Iterable<DogImageDTO> getDogImagesByBreed(String breed) {
		log.info("Getting all dogs of breed={}", breed);
		return this.dogImageRepository.findAllDTOByBreed(breed);
	}

	public DogImageDTO getDogImage(Integer imageId) {
		Optional<DogImageDTO> dogImageDTO = this.getDogImageOptional(imageId);
		if(dogImageDTO.isPresent()){
			return dogImageDTO.get();
		}else{
			String errorMessage = String.format("Dog Image is Not Found imageId='%d'", imageId);
			log.warn(errorMessage);
			throw new DogImageNotFoundException(errorMessage);
		}
	}

	public Optional<DogImageDTO> getDogImageOptional(Integer imageId) {
		log.info("Getting Dog Image by Id={}", imageId);
		return this.dogImageRepository.findDTOById(imageId);
	}
}
