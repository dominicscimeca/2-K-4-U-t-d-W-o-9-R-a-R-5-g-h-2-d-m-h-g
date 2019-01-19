package com.disney.studios.dogimage;

import java.net.MalformedURLException;
import java.net.URL;

public class DogImageBuilder {
	static DogImage build(String url, String breed) throws MalformedURLException {
		return DogImageBuilder.build(new URL(url),breed);
	}
	static DogImage build(URL url){
		return DogImageBuilder.build(url,"Default Breed Name From DogImageBuilder");
	}
	static DogImage build(URL url, String breed){
		return new DogImage(url, breed);
	}
}
