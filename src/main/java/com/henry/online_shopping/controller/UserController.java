package com.henry.online_shopping.controller;

import com.henry.online_shopping.annotation.OneLineMapping;
import com.henry.online_shopping.annotation.FasterRestController;
import com.henry.online_shopping.entity.User;
import com.henry.online_shopping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FasterRestController(url = "/api/v1/user", name = "User")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @OneLineMapping(method = RequestMethod.GET, status = HttpStatus.OK)
    public List<User> getAll() {
        return service.getAll();
    }
}
