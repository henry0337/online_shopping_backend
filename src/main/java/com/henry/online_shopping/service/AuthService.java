package com.henry.online_shopping.service;

import com.henry.online_shopping.dto.response.AuthResponse;
import com.henry.online_shopping.dto.request.ChangePasswordRequest;
import com.henry.online_shopping.dto.request.LoginRequest;
import com.henry.online_shopping.dto.request.RegisterRequest;
import com.henry.online_shopping.entity.User;
import com.henry.online_shopping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager manager;
    private final JwtService jwtService;

    @NonNull
    public void register(@NonNull RegisterRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .name(request.getName())
                .role(request.getRole())
                .build();

        userRepository.save(user);
    }

    @NonNull
    public AuthResponse login(@NonNull LoginRequest body) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword()));

        User user = userRepository.findByEmail(body.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String jwt = jwtService.generateTokenWithUserInfo(user);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        return new AuthResponse(jwt, refreshToken);
    }

    @Nullable
    public Object changePassword(@NonNull ChangePasswordRequest body) {
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
