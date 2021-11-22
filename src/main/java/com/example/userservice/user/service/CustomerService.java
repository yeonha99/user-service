package com.example.userservice.user.service;

import com.example.userservice.common.LoginDto;
import com.example.userservice.common.ResponseDto;
import com.example.userservice.user.domain.Customer;
import com.example.userservice.user.dto.CustomerCreateDto;
import com.example.userservice.user.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public ResponseDto<Object> createCustomer(CustomerCreateDto customerCreateDto){
        Customer customer=Customer.createCustomer(customerCreateDto.getId(),customerCreateDto.getPw(),customerCreateDto.getUserInfo());
        customerRepository.save(customer);
        return ResponseDto.builder()
                .code(HttpStatus.SC_OK)
                .build();
    }

    public ResponseDto<Object> loginCustomer(LoginDto loginDto){
        Customer customer=customerRepository.findCustomerByIdAndPw(loginDto.getId(), loginDto.getPw()).orElse(null);

        ResponseDto responseDto = ResponseDto.builder().build();
        if(customer==null){
            responseDto.setCode(HttpStatus.SC_UNAUTHORIZED);// 로그인 실패
        }else{
            responseDto.setCode(HttpStatus.SC_OK);// 로그인 성공
        }

        return responseDto;
    }


}
