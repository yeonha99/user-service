package com.example.userservice.user.dto;

import com.example.userservice.user.domain.Sex;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
@Data

public class ManagerCreateDto {
    @NotNull
    private String id;
    @NotNull
    private String pw;
    @NotNull
    private String name;
    @NotNull
    private LocalDate birthday;
    @NotNull
    private Sex sex;
    @NotNull
    private String phoneNum;
    @NotNull
    private int storeId;
}
