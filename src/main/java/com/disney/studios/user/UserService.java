package com.disney.studios.user;

import com.disney.studios.dogimage.vote.exception.UnauthorizedException;
import com.disney.studios.user.exception.InvalidLoginException;
import com.disney.studios.user.exception.NotAValidEmailException;
import com.disney.studios.user.exception.UserAlreadyRegisteredException;
import com.disney.studios.user.exception.UserNotFoundException;
import com.disney.studios.user.jwt.JWTProvider;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Service
public class UserService {

	@Autowired private final UserRepository userRepository;
	@Autowired private final JWTProvider jwtProvider;

	public UserService(UserRepository userRepository, JWTProvider jwtProvider) {
		this.userRepository = userRepository;
		this.jwtProvider = jwtProvider;
	}

	public String register(String email, String password) {
		String passwordHash = createPasswordHash(password);
		User existingUser = this.userRepository.getUserByEmail(email);
		if(!isValidEmailAddress(email)){
			throw new NotAValidEmailException(String.format("Email provided is not a valid email. email='%s'",email));
		}

		if(null != existingUser){
			throw new UserAlreadyRegisteredException(String.format("There is already a registered account under this email. email='%s'",email));
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
			throw new InvalidLoginException("Invalid login credentials. Please try again.");
		}
	}

	public User getUserByEmail(String email) {
		return this.userRepository.getUserByEmail(email);
	}

	public User getUserFromToken(String token) {
		if(!this.jwtProvider.isValid(token)){
			throw new UnauthorizedException("Invalid token. Please sign in again.");
		}
		String email = this.jwtProvider.getEmail(token);

		User user = this.getUserByEmail(email);

		if(null == user){
			throw new UserNotFoundException(String.format("You have a valid token. But the underlying user has not been found. It was probably recently deleted. Please re-register. email-from-token='%s'",email));
		}

		return user;

	}
}
