package com.example.userservice.user.controller;

import com.example.userservice.common.LoginDto;
import com.example.userservice.common.TokenDto;
import com.example.userservice.user.service.BranchManagerService;
import com.example.userservice.user.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-service")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;
    //로그인
    @PostMapping("/bo/login")
    public ResponseEntity<TokenDto> loginCustomer(@RequestBody LoginDto loginDto){
        String token= managerService.loginManager(loginDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);
        int state= HttpStatus.SC_OK; //디폴트 성공 ^^
        if(token==null){
            state= HttpStatus.SC_UNAUTHORIZED;
        }
        return new ResponseEntity<>(new TokenDto(token),httpHeaders,state);
    }
}
