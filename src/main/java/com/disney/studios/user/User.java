package com.disney.studios.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@RequiredArgsConstructor
public class User {
	@Id
	@GeneratedValue
	@Getter private Integer id;
	@Getter private final String email;
	@Getter private String hashedPassword;

	public User(String email, String hashedPassword) {
		this(email);
		this.hashedPassword = hashedPassword;
	}
}
