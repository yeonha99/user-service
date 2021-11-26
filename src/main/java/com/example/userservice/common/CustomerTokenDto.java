package com.example.userservice.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

public class CustomerTokenDto {
    private String token;

    public CustomerTokenDto(String token) {
        this.token = token;
    }
}
