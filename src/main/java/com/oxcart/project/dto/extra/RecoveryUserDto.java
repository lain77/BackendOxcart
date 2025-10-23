package com.oxcart.project.dto.extra;

import com.oxcart.project.entity.Role;

import java.util.List;

public record RecoveryUserDto(

        Long id,
        String email,
        List<Role> roles

) {
}