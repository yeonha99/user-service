package com.example.userservice.user.controller;

import antlr.Token;
import com.example.userservice.common.LoginDto;
import com.example.userservice.common.ResponseDto;
import com.example.userservice.common.TokenDto;
import com.example.userservice.user.dto.CustomerCreateDto;
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

        @PostMapping("/sign-up")
        public ResponseDto<Object> createCustomer(@RequestBody CustomerCreateDto customerCreateDto){
            return customerService.createCustomer(customerCreateDto);
        }

         @PostMapping("/login")
         public ResponseEntity<TokenDto> loginCustomer(@RequestBody LoginDto loginDto){
            String token= customerService.loginCustomer(loginDto);
             HttpHeaders httpHeaders = new HttpHeaders();
             httpHeaders.add("Authorization", "Bearer " + token);
            int state=200;
            if(token==null){
                state= HttpStatus.SC_UNAUTHORIZED;
            }
             return new ResponseEntity<>(new TokenDto(token),httpHeaders,state);
         }

         @GetMapping("/getAuthentication")
        public ResponseDto<Object> getAuthentication(HttpServletRequest request){
             String bearerToken = request.getHeader("Authorization");

             if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
                 return customerService.getAuthentication(bearerToken.substring(7));
             }
            return ResponseDto.builder().code(HttpStatus.SC_UNAUTHORIZED).build();
         }

}
