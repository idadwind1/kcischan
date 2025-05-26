package com.kcischan.kcischan.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kcischan.kcischan.service.SessionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AdminApisInterceptor implements HandlerInterceptor {
  @Autowired
  private SessionService sessionService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    HttpSession session = request.getSession(false);

    if (!sessionService.isUserLoggedIn(session)) {
      response.sendError(HttpStatus.UNAUTHORIZED.value(), "You are not logged in");
      return false;
    }

    return true;
  }
}
