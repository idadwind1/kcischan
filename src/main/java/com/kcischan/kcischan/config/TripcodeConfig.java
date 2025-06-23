package com.kcischan.kcischan.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TripcodeConfig {
  @Value("${tripcode.secret-salt}")
  private String secretSalt;

  public String getSalt() {
    return secretSalt;
  }
}
