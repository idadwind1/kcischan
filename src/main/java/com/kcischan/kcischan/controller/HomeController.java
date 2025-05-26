package com.kcischan.kcischan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kcischan.kcischan.model.Board;
import com.kcischan.kcischan.repository.BoardRepository;

@Controller
public class HomeController {
  @Autowired
  BoardRepository boardRepository;

  @GetMapping("/")
  public String home(Model model) {
    List<Board> boards = boardRepository.findAllVisible();
    model.addAttribute("boards", boards);
    return "index";
  }
}
