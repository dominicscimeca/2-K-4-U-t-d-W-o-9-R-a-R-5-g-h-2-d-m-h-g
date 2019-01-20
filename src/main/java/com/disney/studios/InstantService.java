package com.disney.studios;

import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class InstantService {
	public Instant getInstantNow(){
		return Instant.now();
	}
}
