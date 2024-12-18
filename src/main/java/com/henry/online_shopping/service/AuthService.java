package com.henry.online_shopping.service;

import com.henry.online_shopping.dto.response.AuthResponse;
import com.henry.online_shopping.dto.request.ChangePasswordRequest;
import com.henry.online_shopping.dto.request.LoginRequest;
import com.henry.online_shopping.dto.request.RegisterRequest;
import com.henry.online_shopping.entity.User;
import com.henry.online_shopping.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {

    UserRepository userRepository;
    PasswordEncoder encoder;
    AuthenticationManager manager;
    JwtService jwtService;

    public User register(@NonNull RegisterRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .name(request.getName())
                .role(request.getRole())
                .build();

        return userRepository.save(user);
    }

    public AuthResponse login(@NonNull LoginRequest body) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword()));

        User user = userRepository.findByEmail(body.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String jwt = jwtService.generateTokenWithUserInfo(user);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        return new AuthResponse(jwt, refreshToken);
    }

    public AuthResponse changePassword(@NonNull ChangePasswordRequest body) {
        String token, refreshToken;
        String email = body.getEmail();
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null
                || !Objects.equals(body.getRepeatedPassword(), body.getNewPassword()) // Password doesn't equal with repeated password
                || encoder.matches(body.getNewPassword(), user.getPassword())) // If the current account's password equals the password you have just typed
            return null;

        user.setPassword(encoder.encode(body.getNewPassword()));
        userRepository.save(user);

        token = jwtService.generateTokenWithUserInfo(user);
        refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        return new AuthResponse(token, refreshToken);
    }
}
