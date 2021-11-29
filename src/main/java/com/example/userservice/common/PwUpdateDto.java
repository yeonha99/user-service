package com.example.userservice.common;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PwUpdateDto {
    @NotNull
    String prevPw;
    @NotNull
    String newPw;
}
