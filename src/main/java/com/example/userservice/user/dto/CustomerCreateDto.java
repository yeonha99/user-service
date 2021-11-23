package com.example.userservice.user.dto;

import com.example.userservice.user.domain.UserInfo;
import lombok.Data;

@Data
public class CustomerCreateDto {
    private String id;
    private String pw;
    UserInfo userInfo;
}
