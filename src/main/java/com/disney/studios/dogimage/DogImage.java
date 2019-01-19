package com.disney.studios.dogimage;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.net.URL;

@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DogImage {
	@Id
	@GeneratedValue
	@Getter private Integer id;
	@Getter private URL url;
	@Getter private String breed;

	public DogImage(URL url, String breed){
		this.url = url;
		this.breed = breed;
	}
}
