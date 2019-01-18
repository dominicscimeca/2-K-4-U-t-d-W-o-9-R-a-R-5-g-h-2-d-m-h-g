package com.disney.studios.user;

import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@RequiredArgsConstructor
public class User {
	@Id
	@GeneratedValue
	private Integer id;

	private final String email;
}
