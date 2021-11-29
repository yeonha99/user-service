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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final JwtServiceImpl jwtService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseDto<Object> createCustomer(CustomerCreateDto customerCreateDto){
        String encodedPassword = passwordEncoder.encode(customerCreateDto.getPw());//회원가입 시ㅣ 비밀번호 암호화 추가
        Customer customer=Customer.createCustomer(customerCreateDto.getId(),encodedPassword,
                UserInfo.builder()
                        .phoneNum(customerCreateDto.getPhoneNum())
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

        Customer customer=customerRepository.findById(loginDto.getId()).orElse(null);
        String token=null;
        if(customer!=null&&passwordEncoder.matches(loginDto.getPw(), customer.getPw())) {
        //아이디 비번 일치 하면

            token=jwtService.createToken(UserDto.builder().id(customer.getId()).role("C").build());

        }
        return token;
    }

    //고객 정보 업데이트 하는 기능
    @Transactional
    public ResponseDto<Object> updateMyInfo(CustomerInfoDto customerInfoDto){

        Customer customer=customerRepository.findById(customerInfoDto.getId()).orElse(null);
        ResponseDto<Object> responseDto=ResponseDto.builder().build();
        responseDto.setResultCode(HttpStatus.SC_UNAUTHORIZED);

        //토큰 속 사람의 정보
        if(customer!=null){
            customer.updateCustomer(UserInfo.builder()
                            .name(customerInfoDto.getName())
                            .sex(customerInfoDto.getSex())
                    .phoneNum(customerInfoDto.getPhoneNum())
                    .birthday(customerInfoDto.getBirthday())
                    .build());
            responseDto.setResultCode(HttpStatus.SC_OK);
        }
        return responseDto;
    }

    //고객 비밀번호 변경 기능
    @Transactional
    public ResponseDto<Object> updatePw(String jwt, PwUpdateDto pwUpdateDto){
        Map<String, Object> objectMap=jwtService.getInfo(jwt);
        ResponseDto<Object> responseDto=ResponseDto.builder().build();
        responseDto.setResultCode(HttpStatus.SC_OK);
        Map<String,Object> user = (Map<String, Object>) objectMap.get("user");
        Customer customer=customerRepository.findById((String) user.get("id")).orElse(null);
        //토큰 속 사람의 정보

        if(customer!=null&&passwordEncoder.matches(pwUpdateDto.getPrevPw(), customer.getPw())) {
            //토큰 속 사람의 이전 비밀번호와 폼에서 보낸 이전 비밀번호가 같을 시에만 변경 로직 돌아가게 설정함
            customer.updatePw(passwordEncoder.encode(pwUpdateDto.getNewPw())); //변경 할때도 암호화 ^_^

            responseDto.setResultCode(HttpStatus.SC_OK);
        }
        return responseDto;
    }

    //고객 탈퇴 기능
    @Transactional
    public ResponseDto<Object> deleteCustomer(String jwt, PwDto pwDto){
        Map<String, Object> objectMap=jwtService.getInfo(jwt);
        ResponseDto<Object> responseDto=ResponseDto.builder().build();
        responseDto.setResultCode(HttpStatus.SC_UNAUTHORIZED);
        Map<String,Object> user = (Map<String, Object>) objectMap.get("user");
        Customer customer=customerRepository.findById((String) user.get("id")).orElse(null);

        if(customer!=null&&passwordEncoder.matches(pwDto.getPw(), customer.getPw())) {
            //탈퇴 전 비밀번호 확인 ^ㅡ^
            customerRepository.delete(customer);
            responseDto.setResultCode(HttpStatus.SC_OK);
        }
        return responseDto;
    }

    //토큰 해석하고 고객 정보 보내주는 기능
    public ResponseDto<Object> getMyInfo(String jwt){
        Map<String, Object> objectMap=jwtService.getInfo(jwt);
        ResponseDto<Object> responseDto=ResponseDto.builder().build();
        responseDto.setResultCode(HttpStatus.SC_OK);
        Map<String,Object> user = (Map<String, Object>) objectMap.get("user");

        Customer customer=customerRepository.findById((String) user.get("id")).orElse(null);
        if(customer!=null){
            CustomerInfoDto customerInfoDto=CustomerInfoDto.builder().id(customer.getId())
                    .birthday(customer.getUserInfo().getBirthday())
                    .name(customer.getUserInfo().getName())
                    .sex(customer.getUserInfo().getSex())
                    .phoneNum(customer.getUserInfo().getPhoneNum()).build();
            responseDto.setContext(customerInfoDto);
        }
        return responseDto;
    }


    //아이디 중복 확인
    public ResponseDto<Object> duplicateIdCheck(String id){
        Customer customer=customerRepository.findById(id).orElse(null);
        ResponseDto<Object> responseDto=ResponseDto.builder().build();
        responseDto.setResultCode(HttpStatus.SC_OK);
        if(customer==null){
            responseDto.setContext(BoolDto.builder().check(true).build());
        }else{
            responseDto.setContext(BoolDto.builder().check(false).build());
        }
        return responseDto;
    }


    public CustomerInfoDto getInfoById(String id) {
        Customer customer=customerRepository.findById(id).orElse(null);
        if(customer!=null){
            return CustomerInfoDto.builder()
                    .birthday(customer.getUserInfo().getBirthday())
                    .id(customer.getId())
                    .name(customer.getUserInfo().getName())
                    .sex(customer.getUserInfo().getSex())
                    .phoneNum(customer.getUserInfo().getPhoneNum())
                    .build();
        }
        return null;
    }
}
