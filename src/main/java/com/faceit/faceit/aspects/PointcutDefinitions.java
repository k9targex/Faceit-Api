package com.faceit.faceit.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PointcutDefinitions {
  @Pointcut("execution(* com.faceit.faceit.controller.SecurityController.signup(..))")
  public void signupPointcut() { }

  @Pointcut("execution(* com.faceit.faceit.controller.SecurityController.signin(..))")
  public void signinPointcut() { }

  @Pointcut("execution(* com.faceit.faceit.service.SecurityService.changePas(..))")
  public void editPasswordPointcut() { }

  @Pointcut("execution(* com.faceit.faceit.service.UserService.addPlayerToUser(..))")
  public void addPlayerPointcut() { }

  @Pointcut("execution(* com.faceit.faceit.service.UserService.removePlayer(..))")
  public void removePlayerPointcut() { }

  @Pointcut("execution(* com.faceit.faceit.service.CountryService.editCountryName(..))")
  public void editCountryNamePointcut() { }

  @Pointcut("execution(* com.faceit.faceit.service.UserService.deleteUser(..))")
  public void deleteUserPointcut() { }
}
