package com.example.userservice.user.service;

import com.example.userservice.Jwt.JwtServiceImpl;
import com.example.userservice.common.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtServiceImpl jwtService;

    //토큰까주는 기능
   public ResponseDto<Object> getAuthentication(String jwt) {
        Map<String, Object> objectMap=jwtService.getInfo(jwt);
        ResponseDto responseDto=ResponseDto.builder().build();
        Object object=objectMap.get("user");

        if(object.equals(null)){
            responseDto.setCode(HttpStatus.SC_UNAUTHORIZED);
        }else{
            responseDto.setCode(HttpStatus.SC_OK);
            responseDto.setContext(object);
        }
        return responseDto;

    }
}