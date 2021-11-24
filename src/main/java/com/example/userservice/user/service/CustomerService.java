package com.example.userservice.user.service;

import com.example.userservice.Jwt.JwtServiceImpl;
import com.example.userservice.common.*;
import com.example.userservice.user.domain.Customer;
import com.example.userservice.user.domain.UserInfo;
import com.example.userservice.user.dto.CustomerCreateDto;
import com.example.userservice.user.dto.CustomerInfoDto;
import com.example.userservice.user.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final JwtServiceImpl jwtService;
    private final PasswordEncoder passwordEncoder;

    public ResponseDto<Object> createCustomer(CustomerCreateDto customerCreateDto){
        String encodedPassword = passwordEncoder.encode(customerCreateDto.getPw());//회원가입 시ㅣ 비밀번호 암호화 추가
        System.out.println(customerCreateDto.toString());
        Customer customer=Customer.createCustomer(customerCreateDto.getId(),encodedPassword,
                UserInfo.builder()
                        .phone_num(customerCreateDto.getPhone_num())
                        .sex(customerCreateDto.getSex())
                        .name(customerCreateDto.getName())
                        .birthday(customerCreateDto.getBirthday())
                        .build());

        customerRepository.save(customer);
        return ResponseDto.builder()
                .code(HttpStatus.SC_OK)
                .build();
    }

    public String loginCustomer(LoginDto loginDto){//고객 로그인

        Customer customer=customerRepository.findCustomerById(loginDto.getId()).orElse(null);
        String token=null;
        if(customer!=null&&passwordEncoder.matches(loginDto.getPw(), customer.getPw())) {
        //아이디 비번 일치 하면
            System.out.println("비밀번호 일치 함");
            token=jwtService.createToken(UserDto.builder().id(customer.getId()).role("customer").build());

        }
        return token;
    }

    //고객 정보 업데이트 하는 기능
    public ResponseDto<Object> updateMyInfo(CustomerInfoDto customerInfoDto){

        Customer customer=customerRepository.findCustomerById(customerInfoDto.getId()).orElse(null);
        ResponseDto responseDto=ResponseDto.builder().build();
        responseDto.setCode(HttpStatus.SC_UNAUTHORIZED);

        //토큰 속 사람의 정보
        if(customer!=null){
            customer.updateCustomer(UserInfo.builder()
                            .name(customerInfoDto.getName())
                            .sex(customerInfoDto.getSex())
                            .phone_num(customerInfoDto.getPhone_num())
                    .birthday(customerInfoDto.getBirthday())
                    .build());
            customerRepository.save(customer);
            responseDto.setCode(HttpStatus.SC_OK);
        }
        return responseDto;
    }

    //고객 비밀번호 변경 기능
    public ResponseDto<Object> updatePw(String jwt, PwUpdateDto pwUpdateDto){
        Map<String, Object> objectMap=jwtService.getInfo(jwt);
        ResponseDto responseDto=ResponseDto.builder().build();
        responseDto.setCode(HttpStatus.SC_OK);
        Map<String,Object> user = (Map<String, Object>) objectMap.get("user");
        Customer customer=customerRepository.findCustomerById((String) user.get("id")).orElse(null);
        //토큰 속 사람의 정보
        System.out.println(customer.getPw());
        if(customer!=null&&passwordEncoder.matches(pwUpdateDto.getPrev_pw(), customer.getPw())) {
            //토큰 속 사람의 이전 비밀번호와 폼에서 보낸 이전 비밀번호가 같을 시에만 변경 로직 돌아가게 설정함
            customer.updatePw(passwordEncoder.encode(pwUpdateDto.getNew_pw())); //변경 할때도 암호화 ^_^

            customerRepository.save(customer);
            responseDto.setCode(HttpStatus.SC_OK);
        }
        return responseDto;
    }

    //고객 탈퇴 기능
    public ResponseDto<Object> deleteCustomer(String jwt, StringDto stringDto){
        Map<String, Object> objectMap=jwtService.getInfo(jwt);
        ResponseDto responseDto=ResponseDto.builder().build();
        responseDto.setCode(HttpStatus.SC_OK);
        Map<String,Object> user = (Map<String, Object>) objectMap.get("user");
        Customer customer=customerRepository.findCustomerById((String) user.get("id")).orElse(null);

        if(customer!=null&&passwordEncoder.matches(stringDto.getString(), customer.getPw())) {
            //탈퇴 전 비밀번호 확인 ^ㅡ^
            customerRepository.delete(customer);
            responseDto.setCode(HttpStatus.SC_OK);
        }
        return responseDto;
    }

    //토큰 해석하고 고객 정보 보내주는 기능
    public ResponseDto<Object> getMyInfo(String jwt){
        Map<String, Object> objectMap=jwtService.getInfo(jwt);
        ResponseDto responseDto=ResponseDto.builder().build();
        responseDto.setCode(HttpStatus.SC_OK);
        Map<String,Object> user = (Map<String, Object>) objectMap.get("user");

        Customer customer=customerRepository.findCustomerById((String) user.get("id")).orElse(null);
        if(customer!=null){
            CustomerInfoDto customerInfoDto=CustomerInfoDto.builder().id(customer.getId())
                    .birthday(customer.getUserInfo().getBirthday())
                    .name(customer.getUserInfo().getName())
                    .sex(customer.getUserInfo().getSex())
                    .phone_num(customer.getUserInfo().getPhone_num()).build();
            responseDto.setContext(customerInfoDto);
        }
        return responseDto;
    }

    //토큰 해석하고 토큰 안의 정보 반환해주는 기능
    public ResponseDto<Object> getAuthentication(String jwt) {
        Map<String, Object> objectMap=jwtService.getInfo(jwt);
        ResponseDto responseDto=ResponseDto.builder().build();
        Object object=objectMap.get("user");

        if(object.equals(null)){
            responseDto.setCode(HttpStatus.SC_UNAUTHORIZED);
        }else{
            responseDto.setCode(HttpStatus.SC_OK);
            responseDto.setContext(object);
        }
        return responseDto;

    }

    //아이디 중복 확인
    public ResponseDto<Object> duplicateIdCheck(String id){
        Customer customer=customerRepository.findCustomerById(id).orElse(null);
        ResponseDto responseDto=ResponseDto.builder().build();
        responseDto.setCode(HttpStatus.SC_OK);
        if(customer==null){
            responseDto.setContext(BoolDto.builder().check(true).build());
        }else{
            responseDto.setContext(BoolDto.builder().check(false).build());
        }
        return responseDto;
    }


}
