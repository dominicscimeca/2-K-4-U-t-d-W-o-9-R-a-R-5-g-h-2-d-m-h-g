package com.disney.studios.user;

import com.disney.studios.dogimage.vote.NotAuthorizedException;
import com.disney.studios.dogimage.vote.UserNotFoundException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final JWTProvider jwtProvider;

	public UserService(UserRepository userRepository, JWTProvider jwtProvider) {
		this.userRepository = userRepository;
		this.jwtProvider = jwtProvider;
	}

	public String register(String email, String password) {
		String passwordHash = createPasswordHash(password);
		User existingUser = this.userRepository.getUserByEmail(email);
		if(!isValidEmailAddress(email)){
			throw new NotAValidEmailException();
		}

		if(null != existingUser){
			throw new UserAlreadyRegisteredException();
		}

		User newUser = new User(email, passwordHash);
		this.userRepository.save(newUser);

		return this.jwtProvider.constructJWT(newUser);
	}

	private String createPasswordHash(String password) {
		String salt = "thereisnotplacelikehome";
		return DigestUtils.md5Hex(password + salt);
	}

	private boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	public String login(String email, String password) {
		String hashedPassword = createPasswordHash(password);
		User user = this.userRepository.getUserByEmailAndHashedPassword(email, hashedPassword);
		if(null != user){
			return this.jwtProvider.constructJWT(user);
		}else{
			throw new InvalidLoginException();
		}
	}

	public User getUserByEmail(String email) {
		return this.userRepository.getUserByEmail(email);
	}

	public User getUserFromToken(String token) {
		if(!this.jwtProvider.isValid(token)){
			throw new NotAuthorizedException();
		}
		String email = this.jwtProvider.getEmail(token);

		User user = this.getUserByEmail(email);

		if(null == user){
			throw new UserNotFoundException();
		}

		return user;

	}
}
