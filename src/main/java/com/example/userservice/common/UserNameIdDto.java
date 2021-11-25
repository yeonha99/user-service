package com.example.userservice.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserNameIdDto {
    private String id;
    private String name;
}
