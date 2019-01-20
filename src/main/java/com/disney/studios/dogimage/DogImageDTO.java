package com.disney.studios.dogimage;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
public class DogImageDTO {
	@Getter private Integer id;
	@Getter private String url;
	@Getter private String breed;
	@Getter private Long voteCount;

	/**
	 * Constructor needed for Spring Repository to build DogImageDTO from DogImage
	 */
	public DogImageDTO(DogImage dogImage, Long voteCount){
		this.id = dogImage.getId();
		this.url = dogImage.getUrl().toString();
		this.breed = dogImage.getBreed();
		this.voteCount = voteCount;
	}
}
