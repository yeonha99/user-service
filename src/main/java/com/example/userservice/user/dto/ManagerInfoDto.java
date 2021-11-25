package com.example.userservice.user.dto;

import com.example.userservice.user.domain.Sex;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
public class ManagerInfoDto {
    public String id;
    public String name;
    public LocalDate birthday;
    public Sex sex;
    public String phone_num;
    public String store_name;


}
