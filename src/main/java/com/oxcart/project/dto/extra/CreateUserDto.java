package com.oxcart.project.dto.extra;

// Alterado de RoleName para String
public record CreateUserDto(
        String username,
        String email,
        String password,
        String role
) {
}