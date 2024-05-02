package com.faceit.faceit.aspects;

import com.faceit.faceit.model.dto.SignInRequest;
import com.faceit.faceit.model.dto.SignUpRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
  @AfterReturning("PointcutDefinitions.signupPointcut()")
  public void logSignup(JoinPoint joinPoint) {
    String username = null;
    Object[] args = joinPoint.getArgs();
    for (Object obj : args) {
      if (obj instanceof SignUpRequest signupRequest) {
        username = signupRequest.getUsername();
      }
    }
    log.info("Пользователь {" + username + "} зарегистрировал аккаунт");
  }

  @AfterReturning("PointcutDefinitions.signinPointcut()")
  public void logSignin(JoinPoint joinPoint) {
    String username = null;
    Object[] args = joinPoint.getArgs();
    for (Object obj : args) {
      if (obj instanceof SignInRequest signinRequest) {
        username = signinRequest.getUsername();
      }
    }
    log.info("Пользователь {" + username + "} вошел в аккаунт");
  }

  @AfterReturning(pointcut = "PointcutDefinitions.editPasswordPointcut()", returning = "username")
  public void logEditPassword(JoinPoint joinPoint, String username) {
    log.info(String.format("User {%s} changed his password", username));
  }

  @AfterReturning(pointcut = "PointcutDefinitions.addPlayerPointcut()")
  public void logAddPlayer(JoinPoint joinPoint) {
    Object[] args = joinPoint.getArgs();
    String username = (String) args[0];
    String nickname = (String) args[1];
    log.info("Игрок {" + nickname + "} был добавлен пользователю {" + username + "}");
  }

  @AfterReturning(pointcut = "PointcutDefinitions.removePlayerPointcut()")
  public void logRemovePlayer(JoinPoint joinPoint) {
    Object[] args = joinPoint.getArgs();
    String username = (String) args[0];
    String nickname = (String) args[1];
    log.info(
        "Игрок {"
            + nickname
            + "} был удален из списка любимых игроков пользователя {"
            + username
            + "}");
  }

  @AfterReturning("PointcutDefinitions.editCountryNamePointcut()")
  public void logEditCountry(JoinPoint joinPoint) {
    Object[] args = joinPoint.getArgs();
    String countryName = (String) args[0];
    String newCountryName = (String) args[1];
    log.info("Название страны  {" + countryName + "} было изменено на {" + newCountryName + "}");
  }

  @AfterReturning("PointcutDefinitions.deleteUserPointcut()")
  public void logDeleteUser(JoinPoint joinPoint) {
    Object[] args = joinPoint.getArgs();
    String username = (String) args[0];
    log.info("Аккаунт пользователя {" + username + "} был удален");
  }

  @AfterReturning(value = "PointcutDefinitions.getPointcut()", returning = "object")
  public void logGetCrate(JoinPoint joinPoint, Object object) {
    log.info("Returned {}", object);
  }

  @Before("PointcutDefinitions.getPointcut()")
  public void logGetCall(JoinPoint joinPoint) {
    String signatureMethod = joinPoint.getSignature().getName();
    Object[] args = joinPoint.getArgs();
    log.info("Method {} was called with arguments {}", signatureMethod, args);
  }

  @AfterReturning(pointcut = "PointcutDefinitions.incrementCounter()", returning = "result")
  public void logCounter(Object result) {
    log.info("Counter: {}", result);
  }
}
