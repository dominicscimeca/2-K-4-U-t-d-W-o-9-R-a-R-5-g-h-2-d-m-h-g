package com.disney.studios.user;

import com.disney.studios.dogimage.vote.exception.UnauthorizedException;
import com.disney.studios.user.exception.InvalidLoginException;
import com.disney.studios.user.exception.NotAValidEmailException;
import com.disney.studios.user.exception.UserAlreadyRegisteredException;
import com.disney.studios.user.exception.UserNotFoundException;
import com.disney.studios.user.jwt.JWTProvider;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Service
@AllArgsConstructor
public class UserService {
	private final Logger log = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepository;
	private final JWTProvider jwtProvider;

	public UserToken register(String email, String password) {
		String passwordHash = createPasswordHash(password);
		User existingUser = this.userRepository.getUserByEmail(email);
		if(!isValidEmailAddress(email)){
			String message = String.format("Registration Failed: Email provided is not a valid email. email='%s'", email);
			log.warn(message);
			throw new NotAValidEmailException(message);
		}

		if(null != existingUser){
			String message = String.format("Registration Failed: There is already a registered account under this email. email='%s'", email);
			log.warn(message);
			throw new UserAlreadyRegisteredException(message);
		}

		log.info("Creating a new user. email={}", email);
		User newUser = this.userRepository.save(new User(email, passwordHash));

		log.info("Creating a jwt for a new user. user={}", newUser);
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

	public UserToken login(String email, String password) {
		String hashedPassword = createPasswordHash(password);
		User user = this.userRepository.getUserByEmailAndHashedPassword(email, hashedPassword);
		if(null != user){
			return this.jwtProvider.constructJWT(user);
		}else{
			String message = String.format("Invalid login credentials. Please try again. email='%s'", email);
			log.warn(message);
			throw new InvalidLoginException(message);
		}
	}

	public User getUserByEmail(String email) {
		return this.userRepository.getUserByEmail(email);
	}

	public User getUserFromToken(String token) {
		if(!this.jwtProvider.isValid(token)){
			String message = String.format("Invalid token. Please sign in again. token='%s'", token);
			log.warn(message);
			throw new UnauthorizedException(message);
		}
		String email = this.jwtProvider.getEmail(token);

		User user = this.getUserByEmail(email);

		if(null == user){
			String message = String.format("You have a valid token. But the underlying user has not been found. It was probably recently deleted. Please re-register. email-from-token='%s'", email);
			log.warn(message);
			throw new UserNotFoundException(message);
		}

		log.info("Returning User, obtained from token. email={}", email);
		return user;

	}

	public User getUserByAuthHeader(String authHeader) {
		String token = this.jwtProvider.getTokenFromHeader(authHeader);
		String email = this.jwtProvider.getEmail(token);
		return this.getUserByEmail(email);
	}
}
