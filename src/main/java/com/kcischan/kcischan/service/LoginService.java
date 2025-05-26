package com.kcischan.kcischan.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginService {
  private static final long COOLDOWN_SECONDS = 60 * 3;
  private static final int MAX_ATTEMPTS = 5;

  private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

  private final Map<String, Attempt> attempts = new ConcurrentHashMap<>();

  public boolean isBlocked(String key) {
    Attempt attempt = getAttempt(key);
    if (attempt != null && attempt.count >= MAX_ATTEMPTS) {
      long sinceLast = Instant.now().getEpochSecond() - attempt.lastAttempt;
      return sinceLast < COOLDOWN_SECONDS;
    }
    return false;
  }

  public int getRemainingAttempts(String key) {
    Attempt attempt = getAttempt(key);
    if (attempt == null) {
      return MAX_ATTEMPTS;
    }
    return Math.max(0, MAX_ATTEMPTS - attempt.count);
  }

  private Attempt getAttempt(String key) {
    Attempt attempt = attempts.get(key);
    if (attempt == null)
      return null;
    return attempt;
  }

  public void loginFailed(String key) {
    logger.warn("Failed login attempt for user: {}", key);
    Attempt attempt = attempts.getOrDefault(key, new Attempt());
    attempt.count++;
    attempt.lastAttempt = Instant.now().getEpochSecond();
    attempts.put(key, attempt);
  }

  public void loginSucceeded(String key) {
    attempts.remove(key);
  }

  static class Attempt {
    int count = 0;
    long lastAttempt = 0;
  }

  @Autowired
  private Argon2PasswordEncoder passwordEncoder;

  public String hashPassword(String password) {
    return passwordEncoder.encode(password);
  }

  public boolean verifyPassword(String rawPassword, String hashedPassword) {
    if (hashedPassword == null || hashedPassword.isBlank()) {
      logger.warn("One of the hashed password is null or blank! Treating as valid for raw password");
      return true;
    }
    return passwordEncoder.matches(rawPassword, hashedPassword);
  }
}
