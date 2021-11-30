package com.example.userservice.common;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ResponseDto<T> {
    private int resultCode;
    private int totalPage;
    private T context;

    @Builder
    public ResponseDto(int code, T context,int totalPage){
        this.context=context;
        this.resultCode=code;
        this.totalPage=totalPage;
    }


}
