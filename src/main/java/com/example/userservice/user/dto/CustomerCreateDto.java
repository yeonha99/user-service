package com.example.userservice.user.dto;

import com.example.userservice.user.domain.Sex;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class CustomerCreateDto {
    @NotBlank
    private String id;
    @NotBlank
    private String pw;
    @NotBlank
    private String name;
    @NotBlank
    private LocalDate birthday;

    private Sex sex;
    @NotBlank
    private String phoneNum;
}
