package com.example.userservice.common;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PwDto {
    @NotNull
    private String pw;
}
