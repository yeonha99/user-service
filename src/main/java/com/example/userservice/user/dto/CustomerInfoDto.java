package com.example.userservice.user.dto;

import com.example.userservice.user.domain.Sex;

import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotBlank;

import java.time.LocalDate;
@Data
@Builder
public class CustomerInfoDto {
    @NotBlank
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private LocalDate birthday;
    @NotBlank
    private Sex sex;
    @NotBlank
    private String phoneNum;
}
