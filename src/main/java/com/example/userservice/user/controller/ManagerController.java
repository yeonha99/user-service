package com.example.userservice.user.controller;

import com.example.userservice.common.LoginDto;
import com.example.userservice.common.PwUpdateDto;
import com.example.userservice.common.ResponseDto;
import com.example.userservice.common.TokenDto;
import com.example.userservice.user.dto.CustomerInfoDto;
import com.example.userservice.user.dto.ManagerInfoDto;
import com.example.userservice.user.service.BranchManagerService;
import com.example.userservice.user.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    @GetMapping("/bo/info")//내 정보 확인
    public ResponseDto<Object> myInfo(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return managerService.getMyInfo(bearerToken.substring(7));

        }
        return ResponseDto.builder().code(HttpStatus.SC_UNAUTHORIZED).build();
    }

    @PutMapping("/bo/info")//내 정보 수정
    public ResponseDto<Object> myInfoUpdate(@RequestBody ManagerInfoDto managerInfoDto){
        return managerService.updateMyInfo(managerInfoDto);
    }

    @PutMapping("/bo/info/pw")//내 비밀번호 수정
    public ResponseDto<Object> myPwUpdate(HttpServletRequest request,@RequestBody PwUpdateDto pwUpdateDto){
        String bearerToken=request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken)&&bearerToken.startsWith("Bearer ")){
            return managerService.updatePw(bearerToken.substring(7),pwUpdateDto);
        }
        return ResponseDto.builder().code(HttpStatus.SC_UNAUTHORIZED).build();

    }

}
