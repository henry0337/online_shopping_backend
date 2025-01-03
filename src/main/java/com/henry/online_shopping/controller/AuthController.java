package com.henry.online_shopping.controller;

import com.henry.online_shopping.annotation.FasterRestController;
import com.henry.online_shopping.dto.response.AuthResponse;
import com.henry.online_shopping.dto.request.ChangePasswordRequest;
import com.henry.online_shopping.dto.request.LoginRequest;
import com.henry.online_shopping.dto.request.RegisterRequest;
import com.henry.online_shopping.dto.response.UserResponse;
import com.henry.online_shopping.entity.User;
import com.henry.online_shopping.service.AuthService;
import com.henry.online_shopping.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@FasterRestController(url = "/api/v1/auth", name = "Authentication")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    AuthService authService;
    JwtService jwtService;

    /**
     * Register an account.
     *
     * @param request Information about new account to be registered.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    /**
     * Send a request to authenticate with the server.
     *
     * @param request Account info to be used to authenticate.
     * @return Token and refresh token for that user if the authentication successful.
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    /**
     * Obtain user credentials by decoding JWT token.
     *
     * @param token The token to be used for decoding.
     * @return User credentials if it can be found and obtained after decoding, otherwise <code>null</code>.
     * @apiNote I highly recommend that you should test this method by using <b>Postman</b>, not by using <b>Swagger</b>. <br>
     * But if you want to try <b>Swagger</b> for this, please do not use the <code>token</code> parameter, instead, you can use <code>Bearer Token</code>
     * field to fill your token, that one will work as expected.
     */
    @GetMapping("/userInfo")
    @Operation(security = {@SecurityRequirement(name = "Bearer Token")})
    public User obtainUserInfoUsingJwtToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String obtainedToken = null;

        if (token == null) {
            obtainedToken = extractJwtTokenFromSecurityContext();
        } else if (token.startsWith("Bearer ")) {
            obtainedToken = token.substring(7);
        }

        return jwtService.obtainUserFromToken(obtainedToken);
    }

    /**
     * Send the change password request into server.
     *
     * @param request Information about new credential.
     * @return The latest token after a successfully response.
     */
    @PostMapping("/changePassword")
    public AuthResponse changePassword(@RequestBody ChangePasswordRequest request) {
        return authService.changePassword(request);
    }

    private String extractJwtTokenFromSecurityContext() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            return jwtAuth.getToken().getTokenValue();
        } else {
            log.warn("SecurityContext did not find any token. Did you forget using \"Bearer Token\" field ?");
            throw new IllegalStateException("SecurityContext doesn't contain any token");
        }
    }
}
