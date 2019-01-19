package com.disney.studios.dogimage.vote;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Vote {
	public static final Integer UP = 1;
	public static final Integer DOWN = -1;

	@Id
	@GeneratedValue
	private Integer id;
	private Integer dog;
	private Integer vote;
	private Integer user;

	public Vote(Integer dog, Integer vote, Integer user){
		this.dog = dog;
		this.vote = vote;
		this.user = user;
	}
}
