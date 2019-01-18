package com.disney.studios.user;

import com.auth0.jwt.algorithms.Algorithm;
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


	JWTProvider() throws NoSuchAlgorithmException {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(2048);
		KeyPair kp = kpg.generateKeyPair();
		RSAPublicKey publicKey = kp.getPublic().;
		RSAPrivateKey privateKey = kp.getPrivate();
		Algorithm algorithmRS = Algorithm.RSA256(publicKey, privateKey);

	}

	public boolean isValid(DecodedJWT jwt) {
		try {
			algorithmRS.verify(jwt);
		}catch(SignatureVerificationException exception){
			return false;
		}
		return true;
	}

	public DecodedJWT constructJWT(User user) {
		return null;
	}
}
