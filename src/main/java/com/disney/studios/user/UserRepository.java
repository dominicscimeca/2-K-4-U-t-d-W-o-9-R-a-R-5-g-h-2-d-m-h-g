package com.disney.studios.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
	User getUserByEmail(String email);

	User getUserByEmailAndHashedPassword(String email, String hashedPassword);
}
