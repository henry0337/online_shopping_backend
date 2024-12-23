package com.henry.online_shopping.dto.response;

import com.henry.online_shopping.entity.Role;
import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String name;
    private String email;
    private Role role = Role.USER;
}