package com.kcischan.kcischan.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TripCodeConfig {

  @Value("${tripcode.secret-salt}")
  private String secretSalt;

  public String getSecretSalt() {
    return secretSalt;
  }
}
