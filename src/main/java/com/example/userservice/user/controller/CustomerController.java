package com.example.userservice.user.controller;

import com.example.userservice.common.*;
import com.example.userservice.user.dto.CustomerCreateDto;
import com.example.userservice.user.dto.CustomerInfoDto;
import com.example.userservice.user.service.CustomerService;
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
public class CustomerController {

        private final CustomerService customerService;

        //회원가입
        @PostMapping("/sign-up")
        public ResponseDto<Object> createCustomer(@RequestBody CustomerCreateDto customerCreateDto){
            return customerService.createCustomer(customerCreateDto);
        }

        //아이디 중복 확인
         @GetMapping("/sign-up/{id}")
         public ResponseDto<Object> duplicateIdCheck(@PathVariable String id){
             System.out.println(id);
            return customerService.duplicateIdCheck(id);
         }

         //로그인
         @PostMapping("/login")
         public ResponseEntity<TokenDto> loginCustomer(@RequestBody LoginDto loginDto){
            String token= customerService.loginCustomer(loginDto);
             HttpHeaders httpHeaders = new HttpHeaders();
             httpHeaders.add("Authorization", "Bearer " + token);
            int state=HttpStatus.SC_OK; //디폴트 성공 ^^
            if(token==null){
                state= HttpStatus.SC_UNAUTHORIZED;
            }
             return new ResponseEntity<>(new TokenDto(token),httpHeaders,state);
         }

         @GetMapping("/token") //토큰 해석
        public ResponseDto<Object> getAuthentication(HttpServletRequest request){
             String bearerToken = request.getHeader("Authorization");

             if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
                 return customerService.getAuthentication(bearerToken.substring(7));
             }
            return ResponseDto.builder().code(HttpStatus.SC_UNAUTHORIZED).build();
         }

         @GetMapping("/info")//내 정보 확인
         public ResponseDto<Object> myInfo(HttpServletRequest request){
            String bearerToken = request.getHeader("Authorization");

            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
             return customerService.getMyInfo(bearerToken.substring(7));

            }
        return ResponseDto.builder().code(HttpStatus.SC_UNAUTHORIZED).build();
         }

    @PutMapping("/info")//내 정보 수정
    public ResponseDto<Object> myInfoUpdate(@RequestBody CustomerInfoDto customerInfoDto){
            return customerService.updateMyInfo(customerInfoDto);
    }

    @DeleteMapping("/info") // 고객 탈퇴
    public ResponseDto<Object> deleteCustomer(HttpServletRequest request,@RequestBody StringDto stringDto){
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return customerService.deleteCustomer(bearerToken.substring(7),stringDto);
        }
        return ResponseDto.builder().code(HttpStatus.SC_UNAUTHORIZED).build();
    }

    @PutMapping("/info/pw") //내 비밀번호 변경
    public ResponseDto<Object> myPwUpdate(HttpServletRequest request,@RequestBody PwUpdateDto pwUpdateDto){
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return customerService.updatePw(bearerToken.substring(7),pwUpdateDto);
        }
        return ResponseDto.builder().code(HttpStatus.SC_UNAUTHORIZED).build();
    }


}
