package com.henry.online_shopping.controller;

import com.henry.online_shopping.dto.response.AuthResponse;
import com.henry.online_shopping.dto.request.ChangePasswordRequest;
import com.henry.online_shopping.dto.request.LoginRequest;
import com.henry.online_shopping.dto.request.RegisterRequest;
import com.henry.online_shopping.entity.User;
import com.henry.online_shopping.service.AuthService;
import com.henry.online_shopping.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    /**
     * Register an account.
     * @param request Information about new account to be registered.
     */
    @NonNull
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody RegisterRequest request) {
        authService.register(request);
    }

    /**
     * Send a request to authenticate with the server.
     * @param request Account info to be used to authenticate.
     * @return Token and refresh token for that user if the authentication successful.
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @NonNull
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    /**
     * Obtain user credentials after decoding JWT token.
     *
     * @param token The token to be used for decoding.
     * @return User credentials if it can be found and obtained after decoding, otherwise <code>null</code>.
     * @apiNote I highly recommend that you should test this method by using <b>Postman</b>, not by using <b>Swagger UI</b>. <br>
     * But if you want to try <b>Swagger UI</b> for this, please do not use the <code>token</code> parameter, instead, you can use <code>Bearer Token</code>
     * field to fill your token, that one will work as expected.
     */
    @GetMapping("/userInfo")
    @ResponseStatus(HttpStatus.OK)
    @Operation(security = { @SecurityRequirement(name = "Bearer Token") })
    public User obtainUserInfoUsingJwtToken(@RequestHeader(HttpHeaders.AUTHORIZATION) @Nullable String token) {
        String obtainedToken = null;

        if (token == null) {
            obtainedToken = extractJwtTokenFromSecurityContext();
        } else if (token.startsWith("Bearer ")) {
            obtainedToken = token.substring(7);
        }

        return jwtService.obtainUserFromToken(obtainedToken);
    }

    /**
     * Gửi yêu cầu thay đổi mật khẩu của người dùng lên máy chủ.
     * @return Token mới sau khi đổi mật khẩu.
     */
    @PostMapping("/changePassword")
    @ResponseStatus(HttpStatus.OK)
    public Object changePassword(@RequestBody ChangePasswordRequest request) {
        return authService.changePassword(request);
    }

    private String extractJwtTokenFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Phong cách viết dạng "Pattern Matching for `instanceof`" của Java 14
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            return jwtAuth.getToken().getTokenValue();
        }
        log.warn("SecurityContext did not find any token. Did you forgot using this \"Bearer Token\" field ?");
        throw new IllegalStateException("SecurityContext doesn't contain any token");
    }
}
