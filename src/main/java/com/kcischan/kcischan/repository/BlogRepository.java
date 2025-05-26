package com.kcischan.kcischan.repository;

import org.springframework.data.jpa.repository.*;

import com.kcischan.kcischan.model.Blog;

public interface BlogRepository extends JpaRepository<Blog, String> {
}
