package com.example.userservice.user.dto;

import com.example.userservice.user.domain.Sex;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerCreateDto {
    private String id;
    private String pw;
    private String name;
    private LocalDate birthday;
    private Sex sex;
    private String phoneNum;
}
