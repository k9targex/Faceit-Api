package com.faceit.faceit.exception;

public class PlayerNotFoundException extends RuntimeException {
  public PlayerNotFoundException(String message) {
    super(message);
  }
}
