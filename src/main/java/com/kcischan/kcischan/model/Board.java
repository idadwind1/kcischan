package com.kcischan.kcischan.model;

import jakarta.persistence.*;

@Entity
@Table(name = "boards")
public class Board {
  @Id
  private int id;
  private String name;
  private String description;
  private String pinned_post_id;
  private boolean hidden;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPinnedPostId() {
    return pinned_post_id;
  }

  public void setPinnedPostId(String pinned_post_id) {
    this.pinned_post_id = pinned_post_id;
  }

  public boolean getHidden() {
    return hidden;
  }

  public void setHidden(boolean hidden) {
    this.hidden = hidden;
  }
}
