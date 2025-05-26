package com.kcischan.kcischan.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.kcischan.kcischan.model.Admin;

import jakarta.servlet.http.HttpSession;

@Service
public class SessionService {
  public boolean isUserLoggedIn(HttpSession session) {
    return getLoggedInAdmin(session) != null;
  }

  public void assertLoggedIn(HttpSession session) {
    if (!isUserLoggedIn(session)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not logged in, or session expired");
    }
  }

  public boolean isCaptchaValid(HttpSession session, String userAnswer) {
    String stored = (String) session.getAttribute("captcha");
    session.removeAttribute("captcha");
    return stored != null && stored.equalsIgnoreCase(userAnswer);
  }

  public void assertCaptcha(HttpSession session, String userAnswer) {
    if (!isCaptchaValid(session, userAnswer)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Wrong captcha");
    }
  }

  public Admin getLoggedInAdmin(HttpSession session) {
    if (session == null) {
      return null;
    }
    Object obj = session.getAttribute("loggedIn");
    if (!(obj instanceof Admin)) {
      return null;
    }
    Admin admin = (Admin) obj;
    return admin;
  }
}
