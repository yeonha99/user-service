package com.example.userservice.user.controller;

import com.example.userservice.common.*;
import com.example.userservice.user.dto.ManagerInfoDto;
import com.example.userservice.user.service.ManagerService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/user-service")
@RequiredArgsConstructor
@CrossOrigin
public class ManagerController {
    private final ManagerService managerService;
    static final String AUTHORIZATION ="Authorization";
    //로그인
    @ApiOperation("BO 로그인")
    @PostMapping("/bo/login")
    public ResponseDto<Object> loginCustomer(@Valid @RequestBody LoginDto loginDto){
        ManagerTokenDto managerTokenDto= managerService.loginManager(loginDto); //가입승인이 된 관리자만 로그인 가능하다.

        int state= HttpStatus.SC_OK;
        if(managerTokenDto==null|| managerTokenDto.getToken()==null){
            state= HttpStatus.SC_UNAUTHORIZED;
        }
        return ResponseDto.builder()
                .code(state)
                .context(managerTokenDto)
                .build();
    }

    @ApiOperation("BO 내 정보 확인")
    @GetMapping("/bo/info")//내 정보 확인
    public ResponseDto<Object> myInfo(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTHORIZATION);

        if (StringUtils.hasText(bearerToken)) {
            return managerService.getMyInfo(bearerToken);

        }

        return ResponseDto.builder()
                .code(HttpStatus.SC_UNAUTHORIZED)
                .build();
    }
    @ApiOperation("BO 내 정보 수정")
    @PutMapping("/bo/info")//내 정보 수정
    public ResponseDto<Object> myInfoUpdate(@Valid @RequestBody ManagerInfoDto managerInfoDto){
        return managerService.updateMyInfo(managerInfoDto);
    }

    @PutMapping("/bo/info/pw")//내 비밀번호 수정
    public ResponseDto<Object> myPwUpdate(HttpServletRequest request,@Valid @RequestBody PwUpdateDto pwUpdateDto){
        String bearerToken=request.getHeader(AUTHORIZATION);
        if(StringUtils.hasText(bearerToken)){
            return managerService.updatePw(bearerToken,pwUpdateDto);
        }
        return ResponseDto.builder().code(HttpStatus.SC_UNAUTHORIZED).build();

    }

}
