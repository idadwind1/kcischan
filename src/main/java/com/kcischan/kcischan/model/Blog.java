package com.kcischan.kcischan.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "blogs")
public class Blog {
  @Id
  private int id;
  private String content;
  private LocalDateTime updated_at;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public LocalDateTime getUpdatedAt() {
    return updated_at;
  }

  public void setUpdatedAt(LocalDateTime updated_at) {
    this.updated_at = updated_at;
  }
}
