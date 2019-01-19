package com.disney.studios.dogimage.vote;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@EqualsAndHashCode
@RequiredArgsConstructor
@Entity
public class Vote {
	public static final Integer UP = 1;
	public static final Integer DOWN = -1;

	@Id
	@GeneratedValue
	private Integer id;
	private final Integer dog;
	private final Integer vote;
	private final Integer user;
}
