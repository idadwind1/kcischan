package com.kcischan.kcischan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kcischan.kcischan.model.Blog;
import com.kcischan.kcischan.repository.BlogRepository;

@Controller
public class AboutController {
  @Autowired
  private BlogRepository blogRepository;

  @GetMapping("/about")
  public String about(Model model) {
    Blog about = blogRepository.findById("1").orElse(new Blog());

    model.addAttribute("title", "帮助");
    model.addAttribute("last_update", about.getUpdatedAt());
    model.addAttribute("content", about.getContent());
    return "blog";
  }

  @GetMapping("/about/help")
  public String help(Model model) {
    Blog help = blogRepository.findById("2").orElse(new Blog());

    model.addAttribute("title", "帮助");
    model.addAttribute("last_update", help.getUpdatedAt());
    model.addAttribute("content", help.getContent());
    return "blog";
  }

  @GetMapping("/about/rules")
  public String rules(Model model) {
    Blog rules = blogRepository.findById("3").orElse(new Blog());

    model.addAttribute("title", "规则");
    model.addAttribute("last_update", rules.getUpdatedAt());
    model.addAttribute("content", rules.getContent());
    return "blog";
  }
}
