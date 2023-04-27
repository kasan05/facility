package com.mybank.facility.dto;

import com.mybank.facility.enums.Role;

import jakarta.validation.constraints.NotBlank;

public class UserDto {

    private long id;

    @NotBlank(message = "User Name is Blank")
    private String name;

    @NotBlank(message = "Password is Blank")
    private String password;

    private Role role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
