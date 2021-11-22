package com.example.userservice.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

public class TokenDto {
    private String token;

    public TokenDto(String token) {
        this.token = token;
    }
}
