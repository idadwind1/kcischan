
package com.kcischan.kcischan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@SpringBootApplication
public class WebServerApplication {
  public static void main(String[] args) {
    SpringApplication.run(WebServerApplication.class, args);
  }
}
