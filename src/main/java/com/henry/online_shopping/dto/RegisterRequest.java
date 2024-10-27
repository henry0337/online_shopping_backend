package com.henry.online_shopping.dto;

import com.henry.online_shopping.entity.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role = Role.USER;
}
