package com.example.userservice.common;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class PwDto {
    @NotNull
    private String pw;
}
