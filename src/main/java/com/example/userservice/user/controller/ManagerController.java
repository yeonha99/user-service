package com.example.userservice.user.controller;

import com.example.userservice.common.*;
import com.example.userservice.user.domain.Manager;
import com.example.userservice.user.dto.ManagerInfoDto;
import com.example.userservice.user.service.ManagerService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/user-service")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;
    //로그인
    @ApiOperation("BO 로그인")
    @PostMapping("/bo/login")
    public ResponseEntity<ManagerTokenDto> loginCustomer(@Valid @RequestBody LoginDto loginDto){
        ManagerTokenDto managerTokenDto= managerService.loginManager(loginDto); //가입승인이 된 관리자만 로그인 가능하다.
        HttpHeaders httpHeaders = new HttpHeaders();

        if(managerTokenDto!=null&&managerTokenDto.getToken()!=null)
        httpHeaders.add("Authorization", "Bearer " + managerTokenDto.getToken());

        int state= HttpStatus.SC_OK;
        if(managerTokenDto==null|| managerTokenDto.getToken()==null){
            state= HttpStatus.SC_UNAUTHORIZED;
        }
        return new ResponseEntity<>(managerTokenDto,httpHeaders,state);
    }
    @ApiOperation("BO 내 정보 확인")
    @GetMapping("/bo/info")//내 정보 확인
    public ResponseDto<Object> myInfo(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return managerService.getMyInfo(bearerToken.substring(7));

        }
        ResponseDto responseDto=ResponseDto.builder().code(HttpStatus.SC_UNAUTHORIZED).build();
        System.out.println(responseDto.getContext());
        return responseDto;
    }
    @ApiOperation("BO 내 정보 수정")
    @PutMapping("/bo/info")//내 정보 수정
    public ResponseDto<Object> myInfoUpdate(@Valid @RequestBody ManagerInfoDto managerInfoDto){
        return managerService.updateMyInfo(managerInfoDto);
    }

    @PutMapping("/bo/info/pw")//내 비밀번호 수정
    public ResponseDto<Object> myPwUpdate(HttpServletRequest request,@Valid @RequestBody PwUpdateDto pwUpdateDto){
        String bearerToken=request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken)&&bearerToken.startsWith("Bearer ")){
            return managerService.updatePw(bearerToken.substring(7),pwUpdateDto);
        }
        return ResponseDto.builder().code(HttpStatus.SC_UNAUTHORIZED).build();

    }

}
