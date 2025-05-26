package com.kcischan.kcischan.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
public class Post {
  @Id
  private String id;
  private String parentId; // null if it's a thread
  private int board;
  private String content;
  private String status;
  private String title;
  private LocalDateTime createdAt;
  private String attachmentExtension;

  public Post() {
    this.createdAt = LocalDateTime.now();
    this.status = "visible";
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public int getBoard() {
    return board;
  }

  public void setBoard(int board) {
    this.board = board;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public String getAttachmentExtension() {
    return attachmentExtension;
  }

  public void setAttachmentExtension(String attachmentExtension) {
    this.attachmentExtension = attachmentExtension;
  }
}
