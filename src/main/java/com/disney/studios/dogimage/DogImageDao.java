package com.disney.studios.dogimage;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.net.URL;

@Entity
public class DogImageDao {
	@Id
	@GeneratedValue
	private Integer id;

	private final URL url;

	DogImageDao(URL url) {
		this.url = url;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj){
			return true;
		}
		if(null == obj || obj.getClass() != this.getClass()){
			return false;
		}

		DogImageDao dogImageDao = (DogImageDao) obj;

		return dogImageDao.url.equals(this.url);
	}
}
