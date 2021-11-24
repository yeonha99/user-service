package com.example.userservice.user.service;

import com.example.userservice.Jwt.JwtServiceImpl;
import com.example.userservice.common.LoginDto;
import com.example.userservice.common.UserDto;
import com.example.userservice.user.domain.Manager;
import com.example.userservice.user.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final JwtServiceImpl jwtService;
    private final PasswordEncoder passwordEncoder;
    public String loginManager(LoginDto loginDto){//관리자 로그인

        Manager manager=managerRepository.findManagerById(loginDto.getId()).orElse(null);

        String token=null;

        if(manager!=null){
            String type=manager.getClass().getTypeName();
            if(type.contains("BranchManager")&&passwordEncoder.matches(loginDto.getPw(), manager.getPw())) {
                //아이디 비번 일치 하면 일반 관리자 구분
                System.out.println("지점 관리자 비밀번호 일치 함");
                token = jwtService.createToken(UserDto.builder().id(manager.getId()).role("BM").build());

            }
            else if(type.contains("GeneralManager")&&passwordEncoder.matches(loginDto.getPw(), manager.getPw())){
                //총 관리자 구분
                System.out.println("총 관리자 비밀번호 일치 함");
                token = jwtService.createToken(UserDto.builder().id(manager.getId()).role("GM").build());

            }

        }
        return token;
    }
}
