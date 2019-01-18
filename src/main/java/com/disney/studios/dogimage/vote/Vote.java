package com.disney.studios.dogimage.vote;

import com.disney.studios.dogimage.DogImage;
import com.disney.studios.user.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@AllArgsConstructor
public class Vote {
	public static final Integer UP = 1;
	public static final Integer DOWN = -1;

	private final DogImage dog;
	private final Integer vote;
	private final User user;
}
