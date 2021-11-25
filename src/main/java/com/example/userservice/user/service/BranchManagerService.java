package com.example.userservice.user.service;

import com.example.userservice.Jwt.JwtServiceImpl;
import com.example.userservice.common.BoolDto;
import com.example.userservice.common.LoginDto;
import com.example.userservice.common.ResponseDto;
import com.example.userservice.common.UserDto;
import com.example.userservice.user.domain.BranchManager;
import com.example.userservice.user.domain.Customer;
import com.example.userservice.user.domain.Manager;
import com.example.userservice.user.domain.UserInfo;
import com.example.userservice.user.dto.ManagerCreateDto;
import com.example.userservice.user.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BranchManagerService {
    private final ManagerRepository managerRepository;
    private final JwtServiceImpl jwtService;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public ResponseDto<Object> createManager(ManagerCreateDto managerCreateDto){
        String encodedPassword = passwordEncoder.encode(managerCreateDto.getPw());//회원가입 시ㅣ 비밀번호 암호화 추가

        BranchManager branchManager=BranchManager.createBranchManager(managerCreateDto.getId(),encodedPassword, UserInfo.builder()
                .birthday(managerCreateDto.getBirthday())
                .name(managerCreateDto.getName())
                .sex(managerCreateDto.getSex())
                .phone_num(managerCreateDto.getPhoneNum()).build(), managerCreateDto.getStoreId());
        managerRepository.save(branchManager);
        return ResponseDto.builder()
                .code(HttpStatus.SC_OK)
                .build();

    }

    public ResponseDto<Object> duplicateIdCheck(String id){//관리자 아이디 중복 확인 검사
        Manager manager=managerRepository.findById(id).orElse(null);
        ResponseDto responseDto=ResponseDto.builder().build();
        responseDto.setCode(HttpStatus.SC_OK);
        if(manager==null){
            responseDto.setContext(BoolDto.builder().check(true).build());
        }else{
            responseDto.setContext(BoolDto.builder().check(false).build());
        }
        return responseDto;
    }


}
