package com.example.userservice.common;

import lombok.Builder;
import lombok.Data;

@Data
public class ResponseDto<T> {
    private int code;
    private T context;

    @Builder
    public ResponseDto(int code, T context){
        this.context=context;
        this.code=code;
    }


}
