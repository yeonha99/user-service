package com.example.userservice.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {
    private String id;
    private String role;
}
