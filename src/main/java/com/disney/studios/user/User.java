package com.disney.studios.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue
	@Getter private Integer id;
	@Getter private String email;
	@Getter private String hashedPassword;

	public User(String email, String hashedPassword) {
		this(email);
		this.hashedPassword = hashedPassword;
	}

	public User(String email) {
		this.email = email;
	}
}
