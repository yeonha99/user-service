package com.example.userservice.user.dto;

import com.example.userservice.user.domain.Sex;
import lombok.Data;

import java.time.LocalDate;
@Data
public class ManagerCreateDto {
    private String id;
    private String pw;
    private String name;
    private LocalDate birthday;
    private Sex sex;
    private String phone_num;
    private int store_id;
}
