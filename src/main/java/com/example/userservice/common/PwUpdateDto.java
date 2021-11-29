package com.example.userservice.common;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class PwUpdateDto {
    @NotNull
    String prevPw;
    @NotNull
    String newPw;
}
