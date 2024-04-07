package com.faceit.faceit.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class ControllerExceptionHandler {
  private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<Object> handleUnauthorizedException(
      UnauthorizedException ex, WebRequest request) {
    String errorMessage = "Error 401: Unauthorized - " + ex.getMessage();
    logger.error(errorMessage);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<Object> handleIllegalArgumentException(
      MissingServletRequestParameterException ex, WebRequest request) {
    String errorMessage = "Error 400: Bad request - " + ex.getMessage();
    logger.error(errorMessage);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler({NoResourceFoundException.class})
  public ResponseEntity<Object> handleNoResourceFoundException(
      NoResourceFoundException ex, WebRequest request) {
    String errorMessage = "Error 404: Not Found - " + ex.getMessage();
    logger.error(errorMessage);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
  public ResponseEntity<Object> handleMethodNotSupportedException(
      HttpRequestMethodNotSupportedException ex, WebRequest request) {
    String errorMessage = "Error 405: Method not supported - " + ex.getMessage();
    logger.error(errorMessage);
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(ex.getMessage());
  }

  @ExceptionHandler({RuntimeException.class})
  public ResponseEntity<Object> handleAllExceptions(RuntimeException ex, WebRequest request) {
    String errorMessage = "Error 500: Internal server error - " + ex.getMessage();
    logger.error(errorMessage);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
  }

  @ExceptionHandler({
    UsernameNotFoundException.class,
    CountryNotFoundException.class,
    PlayerNotFoundException.class
  })
  public ResponseEntity<Object> usernameNotFoundException(RuntimeException ex, WebRequest request) {
    String errorMessage = "Error 404: Not Found - " + ex.getMessage();
    logger.error(errorMessage);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }
}
