package com.example.userservice.user.controller;

import com.example.userservice.common.LoginDto;
import com.example.userservice.common.ResponseDto;
import com.example.userservice.common.TokenDto;
import com.example.userservice.user.dto.ManagerCreateDto;
import com.example.userservice.user.service.BranchManagerService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-service")
@RequiredArgsConstructor
public class BranchManagerController {
    private final BranchManagerService branchManagerService;

    //관리자 가입
    @PostMapping("/bo/sign-up")
    public ResponseDto<Object> createManager(@RequestBody ManagerCreateDto managerCreateDto){
        return branchManagerService.createManager(managerCreateDto);
    }
    //아이디 중복 확인
    @GetMapping("/bo/sign-up/{id}")
    public ResponseDto<Object> duplicateIdCheck(@PathVariable String id){
        System.out.println(id);
        return branchManagerService.duplicateIdCheck(id);
    }

    //로그인
    @PostMapping("/bo/login")
    public ResponseEntity<TokenDto> loginCustomer(@RequestBody LoginDto loginDto){
        String token= branchManagerService.loginManager(loginDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);
        int state= HttpStatus.SC_OK; //디폴트 성공 ^^
        if(token==null){
            state= HttpStatus.SC_UNAUTHORIZED;
        }
        return new ResponseEntity<>(new TokenDto(token),httpHeaders,state);
    }

}
