package com.disney.studios.dogimage;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.net.URL;

@AllArgsConstructor
@EqualsAndHashCode
public class DogImageDTO {
	private Integer id;
	private URL url;
	private String breed;
	private Long voteCount;
}
