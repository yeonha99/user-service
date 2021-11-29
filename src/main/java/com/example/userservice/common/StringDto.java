package com.example.userservice.common;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class StringDto {
    @NotNull
    private String string;
}
