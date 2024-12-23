package com.henry.online_shopping.service;

import com.henry.online_shopping.dto.response.AuthResponse;
import com.henry.online_shopping.dto.request.ChangePasswordRequest;
import com.henry.online_shopping.dto.request.LoginRequest;
import com.henry.online_shopping.dto.request.RegisterRequest;
import com.henry.online_shopping.dto.response.UserResponse;
import com.henry.online_shopping.entity.User;
import com.henry.online_shopping.mapper.UserMapper;
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

    UserRepository repository;
    PasswordEncoder encoder;
    AuthenticationManager manager;
    JwtService jwtService;
    UserMapper mapper;

    public UserResponse register(@NonNull RegisterRequest request) {
        UserResponse response = mapper.requestToDto(request);
        User user = mapper.responseToModel(response);
        repository.save(user);
        return response;
    }

    public AuthResponse login(@NonNull LoginRequest body) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword()));

        User user = repository.findByEmail(body.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String jwt = jwtService.generateTokenWithUserInfo(user);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        return new AuthResponse(jwt, refreshToken);
    }

    public AuthResponse changePassword(@NonNull ChangePasswordRequest body) {
        String token, refreshToken;
        String email = body.getEmail();
        User user = repository.findByEmail(email).orElse(null);

        if (user == null
                || !Objects.equals(body.getRepeatedPassword(), body.getNewPassword()) // Repeated password doesn't equal with password field.
                || encoder.matches(body.getNewPassword(), user.getPassword())) // Uh... Maybe you found your lost password :v
            return null;

        user.setPassword(encoder.encode(body.getNewPassword()));
        repository.save(user);

        token = jwtService.generateTokenWithUserInfo(user);
        refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        return new AuthResponse(token, refreshToken);
    }
}
