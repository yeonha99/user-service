package com.example.userservice.user.dto;

import com.example.userservice.user.domain.Sex;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
@Data
@Builder
public class ManagerInfoDto {
    @NotNull
    private String id;
    @NotNull
    private String name;
    @NotNull
    private LocalDate birthday;
    @NotNull
    private Sex sex;
    @NotNull
    private String phoneNum;
    @NotNull
    private String storeName;
}
