package com.kcischan.kcischan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;

import com.kcischan.kcischan.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, String> {
  Optional<Admin> findByUsername(String username);

  Optional<Admin> findByPassword(String password);
}
