package com.oxcart.project.dto.extra;

import com.oxcart.project.entity.RoleName;

public class CreateUserDto {

    public String email;

    public String password;

    public RoleName roleName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}
