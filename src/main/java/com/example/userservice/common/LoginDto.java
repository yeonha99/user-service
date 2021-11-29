package com.example.userservice.common;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class LoginDto {
    @NotNull
    private String id;
    @NotNull
    private String pw;
}
