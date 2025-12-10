package com.oxcart.project.dto.extra;

import com.oxcart.project.entity.RoleName;

public record CreateUserDto(

        String username,
        String email,
        String password,
        RoleName role

) {
}