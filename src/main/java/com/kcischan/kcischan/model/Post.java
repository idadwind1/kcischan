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
  private boolean deleted;
  private boolean fromAdmin;
  private String title;
  private LocalDateTime createdAt;
  private String attachmentExtension;
  private String op;
  private String trip;

  public Post() {
    this.deleted = false;
    this.fromAdmin = false;
    this.createdAt = LocalDateTime.now();
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

  public boolean getDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  public boolean getFromAdmin() {
    return fromAdmin;
  }

  public void setFromAdmin(boolean fromAdmin) {
    this.fromAdmin = fromAdmin;
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

  public String getOp() {
    return op;
  }

  public void setOp(String op) {
    this.op = op;
  }

  public String getTrip() {
    return trip;
  }

  public void setTrip(String trip) {
    this.trip = trip;
  }
}
