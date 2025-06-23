package com.kcischan.kcischan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kcischan.kcischan.config.TripcodeConfig;

import java.security.MessageDigest;
import java.util.Base64;

@Service
public class TripcodeService {
  @Autowired
  private TripcodeConfig tripCodeConfig;

  public String encryptTripcode(String name, String code) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      String seasonedTrip = code + tripCodeConfig.getSalt() + name;
      md.update(seasonedTrip.getBytes());
      byte[] digest = md.digest();
      return Base64.getEncoder().encodeToString(digest).substring(0, 8);
    } catch (Exception e) {
      return null;
    }
  }

  public String encryptTripcode(String tripcode) {
    if (!tripcode.contains("#"))
      return tripcode;
    String[] tripParts = tripcode.split("#", 2);
    String name = tripParts[0], code = tripParts[1];
    return name + " !" + encryptTripcode(name, code);
  }
}
