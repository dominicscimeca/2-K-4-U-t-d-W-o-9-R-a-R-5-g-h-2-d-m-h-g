package com.disney.studios.dogimage;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.net.URL;

@AllArgsConstructor
@EqualsAndHashCode
public class DogImageDTO {
	@Getter private Integer id;
	@Getter private URL url;
	@Getter private String breed;
	@Getter private Long voteCount = 0L;

	public DogImageDTO(DogImage dogImage, Long voteCount){
		this.id = dogImage.getId();
		this.url = dogImage.getUrl();
		this.breed = dogImage.getBreed();
		this.setVoteCount(voteCount);
	}

	public void setVoteCount(Long voteCount){
		System.out.println(voteCount);
		if( null == voteCount ){
			this.voteCount = 0L;
		}else{
			this.voteCount = voteCount;
		}
	}
}
