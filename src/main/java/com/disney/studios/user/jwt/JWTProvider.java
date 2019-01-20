package com.disney.studios.user.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.disney.studios.InstantService;
import com.disney.studios.dogimage.vote.exception.UnauthorizedException;
import com.disney.studios.user.User;
import com.disney.studios.user.UserToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

import static java.time.temporal.ChronoUnit.HOURS;

@Component
public class JWTProvider {
	private final Logger log = LoggerFactory.getLogger(JWTProvider.class);

	private final InstantService instantService;
	private Algorithm algorithmRS;

	JWTProvider(InstantService instantService) {
		this.instantService = instantService;
		createAlgorithm();
	}

	private void createAlgorithm() {
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(2048);
			KeyPair kp = kpg.generateKeyPair();
			RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
			RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();
			this.algorithmRS = Algorithm.RSA256(publicKey, privateKey);
		}catch(NoSuchAlgorithmException ex){
			log.warn("Issues with creating JWT algo in memory. Rethrowing exception as a runtime because the app cannot recover from not having a JWT algo. Exception={}", ex);
			throw new RuntimeException(ex);
		}
	}

	public boolean isValid(String jwt) {
		try {
			algorithmRS.verify(JWT.decode(jwt));
		}catch(SignatureVerificationException exception){
			log.warn(String.format("Token found to be invalid token='%s'", jwt));
			return false;
		}
		return true;
	}

	public UserToken constructJWT(User user) {
		try {
			Date expiresAt = new Date(instantService.getInstantNow().plus(1, HOURS).toEpochMilli());

			String token = JWT.create()
					.withIssuer("auth0")
					.withSubject(user.getEmail())
					.withExpiresAt(expiresAt)
					.withClaim("userId", user.getId())
					.sign(algorithmRS);

			log.info("Created JWT Token. token={} expiresAt={}", token, expiresAt);
			return new UserToken(user.getEmail(), token, expiresAt);

		} catch (JWTCreationException exception){
			log.warn(String.format("Execution error creating JWT. Almost definition the fault of the app but if it persists I would check the email address provided email='%s'", user.getEmail()));
			throw new RuntimeException(exception);
		}
	}

	public String getEmail(String token){
		return JWT.decode(token).getSubject();
	}

	public Integer getUserId(String token){
		return JWT.decode(token).getClaim("userId").asInt();
	}

	public Date getExpiration(String token) {
		return JWT.decode(token).getExpiresAt();
	}

	public String getTokenFromHeader(String authHeader) {
		if(null == authHeader){
			String message = "Authorization Header missing. Correct Format `Authorization: Bearer $token`";
			log.warn(message);
			throw new UnauthorizedException(message);
		}

		String[] splitHeader = authHeader.split(" ");
		if(!"bearer".equals(splitHeader[0].toLowerCase())){
			String message = String.format("Authorization Header is not a Bearer token. Correct Format `Authorization: Bearer $token` header='%s'", authHeader);
			log.warn(message);
			throw new UnauthorizedException(message);
		}

		if(2 != splitHeader.length){
			String message = String.format("Authorization Header has the incorrect format. Correct Format `Authorization: Bearer $token` header='%s'", authHeader);
			log.warn(message);
			throw new UnauthorizedException(message);
		}

		return splitHeader[1];
	}
}
