package com.example.userservice.user.dto;

import com.example.userservice.user.domain.Sex;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
public class ManagerInfoDto {
    private String id;
    private String name;
    private LocalDate birthday;
    private Sex sex;
    private String phoneNum;
    private String storeName;
}
