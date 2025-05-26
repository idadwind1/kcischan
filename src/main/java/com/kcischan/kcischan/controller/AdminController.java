package com.kcischan.kcischan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

import com.kcischan.kcischan.model.Admin;
import com.kcischan.kcischan.service.SessionService;

@Controller
public class AdminController {
  @Autowired
  private SessionService sessionService;

  @GetMapping("/admin/")
  public String admin(Model model, HttpSession session) {
    if (!sessionService.isUserLoggedIn(session)) {
      return "redirect:/admin/login";
    }
    Admin admin = (Admin) session.getAttribute("loggedIn");
    model.addAttribute("username", admin.getUsername());
    return "admin/index";
  }

  @GetMapping(value = "/api/admin/logout")
  public String logout(HttpSession session) {
    session.removeAttribute("loggedIn");
    return "redirect:/admin/login";
  }

  @GetMapping("/admin/login")
  public String login(HttpSession session) {
    if (sessionService.isUserLoggedIn(session)) {
      return "redirect:/admin/";
    }
    return "admin/login";
  }
}
