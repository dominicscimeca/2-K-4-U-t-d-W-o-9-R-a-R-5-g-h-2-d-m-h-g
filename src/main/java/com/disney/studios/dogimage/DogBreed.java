package com.disney.studios.dogimage;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DogBreed {
	@Id
	private String name;

	DogBreed(){}

	public DogBreed(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj){
			return true;
		}
		if(null == obj || obj.getClass() != this.getClass()){
			return false;
		}

		DogBreed dogbreed = (DogBreed) obj;

		return dogbreed.name.equals(this.name);
	}
}
