package com.example.userservice.user.controller;

import com.example.userservice.common.LoginDto;
import com.example.userservice.common.ResponseDto;
import com.example.userservice.user.dto.CustomerCreateDto;
import com.example.userservice.user.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
         public ResponseDto<Object> loginCustomer(@RequestBody LoginDto loginDto){
         return customerService.loginCustomer(loginDto);
         }
}
