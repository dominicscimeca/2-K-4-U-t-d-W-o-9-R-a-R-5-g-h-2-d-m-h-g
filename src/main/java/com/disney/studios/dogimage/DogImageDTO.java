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
}
