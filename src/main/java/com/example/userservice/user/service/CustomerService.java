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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final JwtServiceImpl jwtService;
    private final PasswordEncoder passwordEncoder;

    public ResponseDto<Object> createCustomer(CustomerCreateDto customerCreateDto){
        String encodedPassword = passwordEncoder.encode(customerCreateDto.getPw());//회원가입 시ㅣ 비밀번호 암호화 추가
        Customer customer=Customer.createCustomer(customerCreateDto.getId(),encodedPassword,customerCreateDto.getUserInfo());
        customerRepository.save(customer);
        return ResponseDto.builder()
                .code(HttpStatus.SC_OK)
                .build();
    }

    public String loginCustomer(LoginDto loginDto){

        Customer customer=customerRepository.findCustomerById(loginDto.getId()).orElse(null);
        String token=null;
        if(customer!=null&&passwordEncoder.matches(loginDto.getPw(), customer.getPw())) {
        //아이디 비번 일치 하면
            System.out.println("비밀번호 일치 함");
            token=jwtService.createToken(UserDto.builder().id(customer.getId()).role("customer").build());
           // responseDto.setContext(TokenDto.builder().token(jwtService.createToken(UserDto.builder().id(customer.getId()).role("customer").build())));
           // responseDto.setCode(HttpStatus.SC_OK);

        }


        //responseDto.setCode(HttpStatus.SC_UNAUTHORIZED);// 로그인 실패


        return token;
    }

    public ResponseDto<Object> getAuthentication(String jwt) {
        Map<String, Object> objectMap=jwtService.getInfo(jwt);
        return ResponseDto.builder()
                .context(objectMap.get("user"))
                .code(HttpStatus.SC_OK).build();
    }
}
