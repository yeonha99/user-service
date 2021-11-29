package com.example.userservice.common;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ResponseDto<T> {
    private int resultCode;
    private T context;

    @Builder
    public ResponseDto(int code, T context){
        this.context=context;
        this.resultCode=code;
    }


}
