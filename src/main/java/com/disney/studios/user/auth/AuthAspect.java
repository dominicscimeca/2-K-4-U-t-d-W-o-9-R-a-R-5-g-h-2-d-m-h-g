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

import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthAspect {
	private final UserService userService;

	@Around(value="@annotation(Auth)")
	public void authenticateAndGetUser(ProceedingJoinPoint joinPoint) throws Throwable {
		String authorizationHeader = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");;
		User user = this.userService.getUserByAuthHeader(authorizationHeader);

		List<Object> newArgs = replaceJoinPointUserArgIfExists(joinPoint, user);

		joinPoint.proceed(newArgs.toArray());
	}

	private List<Object> replaceJoinPointUserArgIfExists(ProceedingJoinPoint joinPoint, User user) {
		List<Object> newArgs = new ArrayList<>();
		for(Object arg: joinPoint.getArgs()){
			if(arg.getClass().equals(User.class)){
				newArgs.add(user);
			}else{
				newArgs.add(arg);
			}
		}
		return newArgs;
	}
}
