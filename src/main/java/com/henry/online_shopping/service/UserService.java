package com.henry.online_shopping.service;

import com.henry.online_shopping.entity.User;
import com.henry.online_shopping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public List<User> getAll() {
        return repository.findAll();
    }

    public UserDetailsService userDetailsService() {
        return username -> repository.findByEmail(username).orElseThrow();
    }
}
