package com.example.userservice.user.service;

import com.example.userservice.Jwt.JwtServiceImpl;
import com.example.userservice.common.LoginDto;
import com.example.userservice.common.ResponseDto;
import com.example.userservice.common.TokenDto;
import com.example.userservice.common.UserDto;
import com.example.userservice.user.domain.Customer;
import com.example.userservice.user.dto.CustomerCreateDto;
import com.example.userservice.user.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final JwtServiceImpl jwtService;

    public ResponseDto<Object> createCustomer(CustomerCreateDto customerCreateDto){
        Customer customer=Customer.createCustomer(customerCreateDto.getId(),customerCreateDto.getPw(),customerCreateDto.getUserInfo());
        customerRepository.save(customer);
        return ResponseDto.builder()
                .code(HttpStatus.SC_OK)
                .build();
    }

    public String loginCustomer(LoginDto loginDto){
        Customer customer=customerRepository.findCustomerByIdAndPw(loginDto.getId(), loginDto.getPw()).orElse(null);

        String token=null;
        if(customer!=null){
            //responseDto.setCode(HttpStatus.SC_UNAUTHORIZED);// 로그인 실패
            //responseDto.setCode(HttpStatus.SC_OK);// 로그인 성공
            token=jwtService.createToken(UserDto.builder().id(customer.getId()).role("customer").build());
        }
        return token;
    }

    public ResponseDto<Object> getAuthentication(String jwt) {
        Map<String, Object> objectMap=jwtService.getInfo(jwt);
        return ResponseDto.builder()
                .context(objectMap.get("user"))
                .code(HttpStatus.SC_OK).build();
    }
}
