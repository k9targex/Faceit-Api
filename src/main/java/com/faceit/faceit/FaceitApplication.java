package com.faceit.faceit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class FaceitApplication {

  public static void main(String[] args) {

    SpringApplication.run(FaceitApplication.class, args);
  }
}
