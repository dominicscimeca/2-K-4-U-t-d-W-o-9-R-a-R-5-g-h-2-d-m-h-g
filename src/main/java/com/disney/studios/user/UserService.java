package com.disney.studios.user;

import com.disney.studios.dogimage.vote.exception.UnauthorizedException;
import com.disney.studios.user.exception.InvalidLoginException;
import com.disney.studios.user.exception.NotAValidEmailException;
import com.disney.studios.user.exception.UserAlreadyRegisteredException;
import com.disney.studios.user.exception.UserNotFoundException;
import com.disney.studios.user.jwt.JWTProvider;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Service
@AllArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final JWTProvider jwtProvider;

	public UserToken register(String email, String password) {
		String passwordHash = createPasswordHash(password);
		User existingUser = this.userRepository.getUserByEmail(email);
		if(!isValidEmailAddress(email)){
			throw new NotAValidEmailException(String.format("Email provided is not a valid email. email='%s'",email));
		}

		if(null != existingUser){
			throw new UserAlreadyRegisteredException(String.format("There is already a registered account under this email. email='%s'",email));
		}

		User newUser = this.userRepository.save(new User(email, passwordHash));

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

	public User getUserByAuthHeader(String authHeader) {
		String token = this.jwtProvider.getTokenFromHeader(authHeader);
		String email = this.jwtProvider.getEmail(token);
		return this.getUserByEmail(email);
	}
}
