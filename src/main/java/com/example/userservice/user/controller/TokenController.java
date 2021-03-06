package com.example.userservice.user.controller;

import com.example.userservice.common.ResponseDto;
import com.example.userservice.user.service.TokenService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController

@RequestMapping("/user-service")
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    @ApiOperation("다른 서비스에서 토큰 해석 요청")
    @PostMapping("/token") //다른 서비스에서 토큰 해석 요청
    public ResponseDto<Object> getAuthentication(@RequestBody Map<String,Object> tokenDto){
        String token= (String) tokenDto.get("token");
        if (token!=null) {
            return tokenService.getAuthentication(token);
        }
        return ResponseDto.builder().code(HttpStatus.SC_UNAUTHORIZED).build();
    }
}
