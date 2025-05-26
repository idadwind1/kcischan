package com.kcischan.kcischan.api;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.kcischan.kcischan.model.Admin;
import com.kcischan.kcischan.model.Post;
import com.kcischan.kcischan.model.Blog;
import com.kcischan.kcischan.model.Board;
import com.kcischan.kcischan.repository.AdminRepository;
import com.kcischan.kcischan.repository.PostRepository;
import com.kcischan.kcischan.repository.BlogRepository;
import com.kcischan.kcischan.repository.BoardRepository;
import com.kcischan.kcischan.service.LoginService;
import com.kcischan.kcischan.service.SessionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpSession;

@RestController
public class AdminApi {
  private static final Logger logger = LoggerFactory.getLogger(AdminApi.class);
  @Autowired
  private SessionService sessionService;
  @Autowired
  private LoginService loginService;
  @Autowired
  private BoardRepository boardRepo;
  @Autowired
  private AdminRepository adminRepo;
  @Autowired
  private PostRepository postRepo;
  @Autowired
  private BlogRepository blogRepo;

  @RequestMapping(value = "/api/login/admin", method = RequestMethod.POST)
  public ResponseEntity<Map<String, Object>> login(
      @RequestParam("username") String username,
      @RequestParam("password") String password,
      @RequestParam("captcha") String captcha,
      HttpSession session) {

    sessionService.assertCaptcha(session, captcha);

    if (username.isBlank()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Username is required");
    }

    if (loginService.isBlocked(username)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Too many login attempts, please try again later");
    }

    Optional<Admin> matchedAdmin = adminRepo.findByUsername(username);
    if (matchedAdmin.isEmpty() || matchedAdmin.get() == null
        || !loginService.verifyPassword(password, matchedAdmin.get().getPassword())) {
      loginService.loginFailed(username);
      throw new ResponseStatusException(HttpStatus.FORBIDDEN,
          "Wrong username or password. " + loginService.getRemainingAttempts(username)
              + " attempts left before cooldown");
    }

    session.setAttribute("loggedIn", matchedAdmin.get());
    loginService.loginSucceeded(username);

    return ResponseEntity.ok(Map.of(
        "success", true,
        "message", "Logged in successfully"));
  }

  @RequestMapping(value = "/api/admin/delete", method = RequestMethod.POST)
  public ResponseEntity<Map<String, Object>> deletePost(@RequestParam("post") String postId, HttpSession session) {
    logger.info("Admin {} deleted post with ID: {}", sessionService.getLoggedInAdmin(session).getUsername(), postId);

    Optional<Post> post = postRepo.findByIdVisible(postId);
    if (post.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Post not found");
    }

    Post newPost = post.get();
    newPost.setStatus("deleted_by_admin");
    postRepo.save(newPost);
    return ResponseEntity.ok(Map.of(
        "success", true,
        "message", "Post deleted successfully"));
  }

  @RequestMapping(value = "/api/admin/recover", method = RequestMethod.POST)
  public ResponseEntity<Map<String, Object>> recoverPost(@RequestParam("post") String postId, HttpSession session) {
    logger.info("Admin {} recovering post with ID: {}", sessionService.getLoggedInAdmin(session).getUsername(), postId);

    Optional<Post> post = postRepo.findById(postId);
    if (post.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Post not found");
    }

    Post newPost = post.get();
    newPost.setStatus("visible");
    postRepo.save(newPost);
    return ResponseEntity.ok(Map.of(
        "success", true,
        "message", "Post recovered successfully"));
  }

  @RequestMapping(value = "/api/admin/edit_blog", method = RequestMethod.POST)
  public ResponseEntity<Map<String, Object>> editBlog(
      @RequestParam("blog") String blogId,
      @RequestParam("content") String content,
      HttpSession session) {
    logger.info("Admin {} edited blog with ID: {}", sessionService.getLoggedInAdmin(session).getUsername(), blogId);

    Optional<Blog> blog = blogRepo.findById(blogId);
    if (blog.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Blog not found");
    }

    Blog newBlog = blog.get();
    newBlog.setContent(content);
    newBlog.setUpdatedAt(LocalDateTime.now());
    blogRepo.save(newBlog);
    return ResponseEntity.ok(Map.of(
        "success", true,
        "message", "Blog updated successfully"));
  }

  @RequestMapping(value = "/api/admin/change_password", method = RequestMethod.POST)
  public ResponseEntity<Map<String, Object>> changePassword(
      @RequestParam("oldPassword") String oldPassword,
      @RequestParam("newPassword") String newPassword,
      HttpSession session) {
    logger.info("Admin {} is changing password", sessionService.getLoggedInAdmin(session).getUsername());

    if (newPassword.isBlank()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "New password is required");
    }

    Admin loggedInAdmin = sessionService.getLoggedInAdmin(session);
    if (loggedInAdmin == null) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You must be logged in to change password");
    }
    Optional<Admin> matchedAdmin = adminRepo
        .findById(String.valueOf(loggedInAdmin.getId()));
    if (matchedAdmin.isEmpty() || matchedAdmin.get() == null
        || !loginService.verifyPassword(oldPassword, matchedAdmin.get().getPassword())) {
      loginService.loginFailed(loggedInAdmin.getUsername());
      throw new ResponseStatusException(HttpStatus.FORBIDDEN,
          "Wrong old password. " + loginService.getRemainingAttempts(loggedInAdmin.getUsername())
              + " attempts left before cooldown");
    }

    Admin newAdmin = matchedAdmin.get();
    newAdmin.setPassword(loginService.hashPassword(newPassword));
    adminRepo.save(newAdmin);
    return ResponseEntity.ok(Map.of(
        "success", true,
        "message", "Password changed successfully"));
  }

  @RequestMapping(value = "/api/admin/get_post", method = RequestMethod.POST)
  public ResponseEntity<Map<String, Object>> getPost(
      @RequestParam("post") String postId, HttpSession session) {
    Optional<Post> post = postRepo.findById(postId);
    logger.info("Admin {} is retrieving post with ID: {}",
        sessionService.getLoggedInAdmin(session).getUsername(), postId);

    if (post.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Post not found");
    }

    return ResponseEntity.ok(Map.of(
        "success", true,
        "message", "Post retrieved successfully",
        "post", post.get()));
  }

  @RequestMapping(value = "/api/admin/change_board_info", method = RequestMethod.POST)
  public ResponseEntity<Map<String, Object>> changeBoardInfo(@RequestParam("boardId") String boardId,
      @RequestParam("name") String newName,
      @RequestParam("description") String newDescription,
      @RequestParam("pinnedPostId") String newPinnedPostId,
      HttpSession session) {
    logger.info("Admin {} is changing board info for board ID: {}",
        sessionService.getLoggedInAdmin(session).getUsername(), boardId);

    if (newName.isBlank() && newDescription.isBlank() && newPinnedPostId.isBlank()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN,
          "At least one of name, description, or pinned post ID must be provided");
    }

    Optional<Board> board = boardRepo.findById(boardId);

    if (board.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Board not found");
    }

    Board newBoard = board.get();
    if (newName != null && !newName.isBlank()) {
      newBoard.setName(newName);
    }
    if (newDescription != null && !newDescription.isBlank()) {
      newBoard.setDescription(newDescription);
    }
    if (newPinnedPostId != null && !newPinnedPostId.isBlank()) {
      Optional<Post> pinnedPost = postRepo.findById(newPinnedPostId);
      if (pinnedPost.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN,
            "Pinned post not found");
      }
      newBoard.setPinnedPostId(pinnedPost.get().getId());
    }

    return ResponseEntity.ok(Map.of(
        "success", true,
        "message", "Board information updated successfully",
        "board", boardRepo.save(newBoard)));
  }

  @RequestMapping(value = "/api/admin/unset_pinned_post", method = RequestMethod.POST)
  public ResponseEntity<Map<String, Object>> unsetPinnedPost(
      @RequestParam("boardId") String boardId,
      HttpSession session) {
    logger.info("Admin {} is unsetting pinned post for board ID: {}",
        sessionService.getLoggedInAdmin(session).getUsername(), boardId);

    Optional<Board> board = boardRepo.findById(boardId);
    if (board.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Board not found");
    }

    Board newBoard = board.get();
    newBoard.setPinnedPostId(null);
    boardRepo.save(newBoard);

    return ResponseEntity.ok(Map.of(
        "success", true,
        "message", "Pinned post unset successfully",
        "board", newBoard));
  }
}
