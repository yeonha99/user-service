package com.example.userservice.common;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginDto {
    @NotNull
    private String id;
    @NotNull
    private String pw;
}
