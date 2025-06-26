package com.kcischan.kcischan.api;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import com.kcischan.kcischan.model.Post;
import com.kcischan.kcischan.repository.PostRepository;
import com.kcischan.kcischan.service.IpLimitService;
import com.kcischan.kcischan.service.SessionService;
import com.kcischan.kcischan.service.TripcodeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

@RestController
public class PostApi {
  @Autowired
  SessionService sessionService;
  @Autowired
  private PostRepository postRepo;
  @Autowired
  private TripcodeService tripcodeService;
  @Autowired
  private IpLimitService ipLimitService;

  @RequestMapping(value = "/api/post", method = RequestMethod.POST)
  public ResponseEntity<Map<String, Object>> post(
      @RequestParam("board") int board,
      @RequestParam("title") String title,
      @RequestParam("content") String content,
      @RequestParam("captcha") String captcha,
      @RequestParam(value = "attachment", required = false) MultipartFile file,
      @RequestParam(value = "parent_id", required = false) String parentId,
      @RequestParam(value = "author", required = false) String author,
      HttpServletRequest request,
      HttpSession session) throws IOException {

    String ip = request.getHeader("X-Forwarded-For");
    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    if (ip == null) {
      ip = "";
    }
    if (ipLimitService.isLimited(ip)) {
      throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "发帖太频繁，请稍后再试");
    }

    sessionService.assertCaptcha(session, captcha);

    if (title.isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "标题不能为空");
    }

    if (content.isBlank() && file.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "内容不能为空");
    }

    if (content.length() > 3000) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "内容太长了 (3000字以内)");
    }

    if (content.length() < 10) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "内容太短了");
    }

    Post newPost = new Post();
    newPost.setId(NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, NanoIdUtils.DEFAULT_ALPHABET, 10));

    if (author != null && !author.isBlank()) {
      if (author.contains("!")) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "发帖人名称不能包含感叹号");
      }
      newPost.setAuthor(tripcodeService.encryptTripcode(author));
    }

    if (sessionService.isUserLoggedIn(session)) {
      newPost.setFromAdmin(true);
    }

    if (file != null && !file.isEmpty()) {
      newPost.setAttachmentExtension(
          file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1));

      Path uploadDir = Paths.get("uploads");
      if (!Files.exists(uploadDir)) {
        Files.createDirectories(uploadDir);
      }

      file.transferTo(uploadDir.resolve(newPost.getId() + "." + newPost.getAttachmentExtension()).normalize()
          .toAbsolutePath().toFile());
    }

    if (parentId.isBlank())
      parentId = null;

    if (parentId != null) {
      Post parentPost = postRepo.findByIdVisible(parentId).orElse(null);
      if (parentId != null && parentPost == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "找不到父帖子");
      }
      if (parentPost.getBoard() != board) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "父帖子不属于同一版块");
      }
    }

    ipLimitService.handlePost(ip);

    newPost.setBoard(board);
    newPost.setContent(content);
    // newPost.setCreatedAt(LocalDateTime.now());
    // newPost.setStatus("visible");
    newPost.setTitle(title);
    newPost.setParentId(parentId);
    Post saved = postRepo.save(newPost);
    return ResponseEntity.ok(Map.of(
        "success", true,
        "message", "帖子已创建！",
        "postId", saved.getId()));
  }

  @RequestMapping(value = "/uploads/{file}.{ext}", method = RequestMethod.GET)
  public ResponseEntity<?> serveFile(@PathVariable("file") String fileName,
      @PathVariable("ext") String fileExtension, HttpSession session) {
    Optional<Post> post = postRepo.findById(fileName);
    if (post.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    if (post.get().getAttachmentExtension() == null
        || !post.get().getAttachmentExtension().equals(fileExtension)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    if (post.get().getDeleted() && sessionService.isUserLoggedIn(session)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    Path file = Paths.get("uploads", fileName + "." + fileExtension);
    Resource resource = new FileSystemResource(file.toFile());
    if (!resource.exists() || !resource.isReadable()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + post.get().getTitle().replaceAll("\\s+", "_") + "."
                + fileExtension + "\"")
        .contentType(MediaType.parseMediaType("application/octet-stream"))
        .body(resource);
  }
}
