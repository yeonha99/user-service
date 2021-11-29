package com.example.userservice.user.controller;

import com.example.userservice.common.*;
import com.example.userservice.user.dto.CustomerCreateDto;
import com.example.userservice.user.dto.CustomerInfoDto;
import com.example.userservice.user.service.CustomerService;
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
public class CustomerController {

        private final CustomerService customerService;
        static final String AUTHORIZATION ="Authorization";

        //회원가입
        @ApiOperation("FO 회원가입")
        @PostMapping("/sign-up")
        public ResponseDto<Object> createCustomer(@Valid @RequestBody CustomerCreateDto customerCreateDto){
            return customerService.createCustomer(customerCreateDto);
        }

        //아이디 중복 확인
        @ApiOperation("FO 아이디 중복 확인")
         @GetMapping("/sign-up/{id}")
         public ResponseDto<Object> duplicateIdCheck(@PathVariable String id){
            return customerService.duplicateIdCheck(id);
         }

         //로그인
         @ApiOperation("FO 로그인")
         @PostMapping("/login")
         public ResponseDto<Object> loginCustomer(@Valid @RequestBody LoginDto loginDto){
            String token= customerService.loginCustomer(loginDto);
            int state=HttpStatus.SC_OK; //디폴트 성공 ^^
            if(token==null){
                state= HttpStatus.SC_UNAUTHORIZED;
            }
             return ResponseDto.builder()
                     .code(state)
                     .context(CustomerTokenDto.builder().token(token).build())
                     .build();
         }


    @ApiOperation("FO 내 정보 확인")
         @GetMapping("/info")//내 정보 확인
         public ResponseDto<Object> myInfo(HttpServletRequest request){
            String bearerToken = request.getHeader(AUTHORIZATION);

            if (StringUtils.hasText(bearerToken)) {
             return customerService.getMyInfo(bearerToken);

            }
        return ResponseDto.builder().code(HttpStatus.SC_UNAUTHORIZED).build();
         }
    @ApiOperation("FO 내 정보 수정")
    @PutMapping("/info")//내 정보 수정
    public ResponseDto<Object> myInfoUpdate(@Valid @RequestBody CustomerInfoDto customerInfoDto){
            return customerService.updateMyInfo(customerInfoDto);
    }

    @ApiOperation("FO 고객 탈퇴")
    @DeleteMapping("/info") // 고객 탈퇴
    public ResponseDto<Object> deleteCustomer(HttpServletRequest request,@Valid @RequestBody PwDto pwDto){
        String bearerToken = request.getHeader(AUTHORIZATION);

        if (StringUtils.hasText(bearerToken)) {
            return customerService.deleteCustomer(bearerToken, pwDto);
        }
        return ResponseDto.builder().code(HttpStatus.SC_UNAUTHORIZED).build();
    }
    @ApiOperation("FO 내 비밀번호 변경")
    @PutMapping("/info/pw") //내 비밀번호 변경
    public ResponseDto<Object> myPwUpdate(HttpServletRequest request,@Valid @RequestBody PwUpdateDto pwUpdateDto){
        String bearerToken = request.getHeader(AUTHORIZATION);

        if (StringUtils.hasText(bearerToken)) {
            return customerService.updatePw(bearerToken,pwUpdateDto);
        }
        return ResponseDto.builder().code(HttpStatus.SC_UNAUTHORIZED).build();
    }


}
