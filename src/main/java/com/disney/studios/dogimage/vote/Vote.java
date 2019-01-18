package com.disney.studios.dogimage.vote;

import com.disney.studios.dogimage.DogImage;
import com.disney.studios.user.User;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@EqualsAndHashCode
@RequiredArgsConstructor
@Entity
public class Vote {
	public static final Integer UP = 1;
	public static final Integer DOWN = -1;

	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne
	private final DogImage dog;
	private final Integer vote;
	@ManyToOne
	private final User user;
}
