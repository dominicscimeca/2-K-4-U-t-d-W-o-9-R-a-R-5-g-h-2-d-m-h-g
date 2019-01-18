package com.disney.studios.dogimage;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.net.URL;
import java.util.Objects;

@Entity
public class DogImage {
	@Id
	@GeneratedValue
	private Integer id;
	private URL url;

	@ManyToOne
	private DogBreed breed;

	DogImage(){}

	DogImage(URL url, DogBreed breed) {
		this.url = url;
		this.breed = breed;
	}

	public Integer getId() {
		return id;
	}

	public URL getUrl() {
		return url;
	}

	public DogBreed getBreed() {
		return breed;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj){
			return true;
		}
		if(null == obj || obj.getClass() != this.getClass()){
			return false;
		}

		DogImage dogImage = (DogImage) obj;

		return dogImage.url.equals(this.url) && (Objects.equals(dogImage.breed, this.breed));
	}
}
