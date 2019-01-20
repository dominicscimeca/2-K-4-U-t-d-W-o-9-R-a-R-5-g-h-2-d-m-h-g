package com.disney.studios.user.auth;

import com.disney.studios.user.User;
import com.disney.studios.user.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthAspect {
	private final UserService userService;

	@Around(value="@annotation(Auth)")
	public void authenticateAndGetUser(ProceedingJoinPoint joinPoint) throws Throwable {
		String authorizationHeader = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");;
		Integer imageID = (Integer) joinPoint.getArgs()[0];
		User user = this.userService.getUserByAuthHeader(authorizationHeader);

		joinPoint.proceed(new Object[]{imageID, user});
	}
}
