package com.henry.online_shopping.controller;

import com.henry.online_shopping.entity.User;
import com.henry.online_shopping.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Tag(name = "User")
public class UserController {
    private final UserService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public User getUserByEmail(@RequestParam("email") String email) {
        return service.findByEmail(email);
    }
}
