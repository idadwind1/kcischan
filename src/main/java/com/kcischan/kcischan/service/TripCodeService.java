package com.kcischan.kcischan.service;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.Base64;

@Service
public class TripCodeService {
  public String encryptTrip(String key) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(key.getBytes());
      byte[] digest = md.digest();
      return Base64.getEncoder().encodeToString(digest).substring(10);
    } catch (Exception e) {
      return null;
    }
  }
}
