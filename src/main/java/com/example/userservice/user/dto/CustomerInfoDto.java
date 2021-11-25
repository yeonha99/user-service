package com.example.userservice.user.dto;

import com.example.userservice.user.domain.Sex;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Data
@Builder
public class CustomerInfoDto {
    private String id;
    private String name;
    private LocalDate birthday;
    private Sex sex;
    private String phoneNum;
}
