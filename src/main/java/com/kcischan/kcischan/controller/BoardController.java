package com.kcischan.kcischan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.kcischan.kcischan.model.Board;
import com.kcischan.kcischan.model.Post;
import com.kcischan.kcischan.repository.PostRepository;
import com.kcischan.kcischan.service.SessionService;

import jakarta.servlet.http.HttpSession;

import com.kcischan.kcischan.repository.BoardRepository;
import com.kcischan.kcischan.api.AdminApi;

import java.util.*;

@Controller
public class BoardController {

  @Autowired
  private PostRepository postRepo;
  @Autowired
  private BoardRepository boardRepo;
  @Autowired
  private SessionService sessionService;

  @GetMapping("/board/{board}")
  public String board(@PathVariable("board") String boardId, Model model, HttpSession session) {
    List<Post> threads;
    if (sessionService.isUserLoggedIn(session)) {
      threads = postRepo.findByBoardAndParentIdIsNullOrderByCreatedAtDesc(boardId);
    } else {
      threads = postRepo.findByBoardAndParentIdIsNullVisibleOrderByCreatedAtDesc(boardId);
    }

    Optional<Board> board = boardRepo.findById(boardId);
    if (board.isEmpty())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such board found");
    model.addAttribute("threads", threads);
    model.addAttribute("board", board.get());
    if (sessionService.isUserLoggedIn(session))
      model.addAttribute("isAdmin", true);
    else
      model.addAttribute("isAdmin", false);
    if (board.get().getPinnedPostId() != null) {
      Optional<Post> pinnedPost = postRepo.findById(board.get().getPinnedPostId());
      if (!pinnedPost.isEmpty())
        model.addAttribute("pinnedPost", pinnedPost.get());
    }
    return "board";
  }

  @GetMapping("/post/{postId}")
  public String thread(@PathVariable("postId") String threadId, Model model, HttpSession session) {
    Optional<Post> rootOpt;
    if (sessionService.isUserLoggedIn(session))
      rootOpt = postRepo.findById(threadId);
    else
      rootOpt = postRepo.findByIdVisible(threadId);
    if (rootOpt.isEmpty())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such post found");
    Post root = rootOpt.get();
    List<Post> replies;
    if (sessionService.isUserLoggedIn(session))
      replies = postRepo.findByParentIdOrderByCreatedAtAsc(threadId);
    else
      replies = postRepo.findByParentIdVisibleOrderByCreatedAtAsc(threadId);

    model.addAttribute("root", root);
    model.addAttribute("replies", replies);
    model.addAttribute("post", rootOpt.get());
    if (sessionService.isUserLoggedIn(session))
      model.addAttribute("isAdmin", true);
    else
      model.addAttribute("isAdmin", false);
    return "post";
  }
}
