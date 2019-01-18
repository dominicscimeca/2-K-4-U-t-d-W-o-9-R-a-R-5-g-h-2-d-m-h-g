package com.disney.studios.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue
	private Integer id;

	private final String email;
	private String hashedPassword;

	public User(String email){
		this.email = email;
	}

	public User(String email, String hashedPassword) {
		this(email);
		this.hashedPassword = hashedPassword;
	}
}
