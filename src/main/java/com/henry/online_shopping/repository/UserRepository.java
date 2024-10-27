package com.henry.online_shopping.repository;

import com.henry.online_shopping.entity.Role;
import com.henry.online_shopping.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String username);

    List<User> findByRole(Role role);
}
