package com.disney.studios.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Component
public class JWTProvider {

	private Algorithm algorithmRS;

	JWTProvider() throws NoSuchAlgorithmException {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(2048);
		KeyPair kp = kpg.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();
		this.algorithmRS = Algorithm.RSA256(publicKey, privateKey);
	}

	public boolean isValid(String jwt) {
		try {
			algorithmRS.verify(JWT.decode(jwt));
		}catch(SignatureVerificationException exception){
			return false;
		}
		return true;
	}

	public String constructJWT(User user) {
		try {
			return JWT.create()
					.withIssuer("auth0")
					.withClaim("email", user.getEmail())
					.sign(algorithmRS);
		} catch (JWTCreationException exception){
			//Invalid Signing configuration / Couldn't convert Claims.
		}
		return null;
	}

	public String getEmail(String token){
		return JWT.decode(token).getClaim("email").asString();
	}
}
