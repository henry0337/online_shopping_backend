package com.henry.online_shopping.service;

import com.henry.online_shopping.entity.User;
import com.henry.online_shopping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow();
    }

    @NonNull
    public final UserDetailsService userDetailsService() {
        return username -> repository.findByEmail(username).orElseThrow();
    }
}
