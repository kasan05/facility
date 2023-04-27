package com.mybank.facility.dto;

public class AuthResponse {

    private String token;

    private UserDto user;

    public AuthResponse() {
    }

    public AuthResponse(String token, UserDto user) {
        super();
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto userDto) {
        this.user = userDto;
    }

}
