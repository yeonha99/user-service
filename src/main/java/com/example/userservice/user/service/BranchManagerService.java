package com.example.userservice.user.service;

import com.example.userservice.Jwt.JwtServiceImpl;
import com.example.userservice.common.ResponseDto;
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

@Service
@RequiredArgsConstructor
public class BranchManagerService {
    private final ManagerRepository managerRepository;
    private final JwtServiceImpl jwtService;
    private final PasswordEncoder passwordEncoder;

    public ResponseDto<Object> createManager(ManagerCreateDto managerCreateDto){
        String encodedPassword = passwordEncoder.encode(managerCreateDto.getPw());//회원가입 시ㅣ 비밀번호 암호화 추가

        BranchManager branchManager=BranchManager.createBranchManager(managerCreateDto.getId(),encodedPassword, UserInfo.builder()
                .birthday(managerCreateDto.getBirthday())
                .name(managerCreateDto.getName())
                .sex(managerCreateDto.getSex())
                .phone_num(managerCreateDto.getPhone_num()).build(), managerCreateDto.getStore_id());
        managerRepository.save(branchManager);
        return ResponseDto.builder()
                .code(HttpStatus.SC_OK)
                .build();

    }



}
