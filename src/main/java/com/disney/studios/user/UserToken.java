package com.disney.studios.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@AllArgsConstructor
@Data
@EqualsAndHashCode
public class UserToken {
	private String email;
	private String token;
	private Date expiresAt;
}
