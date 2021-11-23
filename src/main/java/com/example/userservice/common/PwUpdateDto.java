package com.example.userservice.common;

import lombok.Data;

@Data
public class PwUpdateDto {
    String prev_pw;
    String new_pw;
}
