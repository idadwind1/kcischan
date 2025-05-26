package com.kcischan.kcischan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;

import com.kcischan.kcischan.model.Board;

public interface BoardRepository extends JpaRepository<Board, String> {
  @Query(value = "SELECT * FROM boards WHERE hidden = 0", nativeQuery = true)
  List<Board> findAllVisible();
}
