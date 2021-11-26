package com.example.userservice.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ManagerTokenDto {
    private String token;
    private int storeId;
    private String role;
    private String storeName;

}
