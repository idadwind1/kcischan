package com.kcischan.kcischan.repository;

import com.kcischan.kcischan.model.Post;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface PostRepository extends JpaRepository<Post, String> {
  @Query(value = "SELECT * FROM posts WHERE board = :board AND parent_id IS NULL AND deleted = 0 ORDER BY created_at DESC", nativeQuery = true)
  List<Post> findByBoardAndParentIdIsNullVisibleOrderByCreatedAtDesc(@Param("board") String board);

  @Query(value = "SELECT * FROM posts WHERE board = :board AND parent_id IS NULL ORDER BY created_at DESC", nativeQuery = true)
  List<Post> findByBoardAndParentIdIsNullOrderByCreatedAtDesc(@Param("board") String board);

  @Query(value = "SELECT * FROM posts WHERE id = :id AND deleted = 0", nativeQuery = true)
  Optional<Post> findByIdVisible(@Param("id") String parentId);

  @Query(value = "SELECT * FROM posts WHERE parent_id = :parentId AND deleted = 0 ORDER BY created_at ASC", nativeQuery = true)
  List<Post> findByParentIdVisibleOrderByCreatedAtAsc(@Param("parentId") String parentId);

  @Query(value = "SELECT * FROM posts WHERE parent_id = :parentId ORDER BY created_at ASC", nativeQuery = true)
  List<Post> findByParentIdOrderByCreatedAtAsc(@Param("parentId") String parentId);

  @Query(value = "SELECT * FROM posts", nativeQuery = true)
  List<Post> findAll();

  @Query(value = "SELECT * FROM posts WHERE deleted = 0", nativeQuery = true)
  List<Post> findAllVisible();
}
