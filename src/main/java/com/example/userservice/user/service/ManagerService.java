package com.example.userservice.user.service;

import com.example.userservice.Jwt.JwtServiceImpl;
import com.example.userservice.common.LoginDto;
import com.example.userservice.common.ResponseDto;
import com.example.userservice.common.UserDto;
import com.example.userservice.user.client.StoreClient;
import com.example.userservice.user.domain.*;
import com.example.userservice.user.dto.ManagerInfoDto;
import com.example.userservice.user.repository.BranchManagerRepository;
import com.example.userservice.user.repository.GeneralManagerRepository;
import com.example.userservice.user.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final BranchManagerRepository branchManagerRepository;
    private final GeneralManagerRepository generalManagerRepository;
    private final JwtServiceImpl jwtService;
    private final PasswordEncoder passwordEncoder;
    private final StoreClient client;

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


    //토큰 해석하고 관리자 정보 보내주는 기능
    public ResponseDto<Object> getMyInfo(String jwt){

        Map<String, Object> objectMap=jwtService.getInfo(jwt);
        ResponseDto responseDto=ResponseDto.builder().build();
        responseDto.setCode(HttpStatus.SC_OK);
        Map<String,Object> user = (Map<String, Object>) objectMap.get("user");

        BranchManager branchManager =branchManagerRepository.findBranchManagerById((String) user.get("id")).orElse(null);
        if (branchManager!=null){//지점 관리일 경우

            Map<String,Object> map=client.findStoreName(branchManager.getStoreId()); //관리자의 매장 이름 매장 서비스에 물어봐서 가져오기
            String store_name= ((String) map.get("store_name"));

            responseDto.setContext(ManagerInfoDto.builder()
                    .birthday(branchManager.getUserInfo().getBirthday())
                    .sex(branchManager.getUserInfo().getSex())
                    .name(branchManager.getUserInfo().getName())
                    .phone_num(branchManager.getUserInfo().getPhone_num())
                    .id(branchManager.getId())
                    .store_name(store_name)
                    .build()
            );
        }else{
            GeneralManager generalManager =generalManagerRepository.findGeneralManagerById((String) user.get("id")).orElse(null);
            if(generalManager!=null){
                responseDto.setContext(ManagerInfoDto.builder()
                        .birthday(generalManager.getUserInfo().getBirthday())
                        .sex(generalManager.getUserInfo().getSex())
                        .name(generalManager.getUserInfo().getName())
                        .phone_num(generalManager.getUserInfo().getPhone_num())
                        .id(generalManager.getId())
                        .store_name(null)
                        .build()  );
            }
        }
        return responseDto;
    }

    public ResponseDto<Object> updateMyInfo(ManagerInfoDto managerInfoDto) {
        Manager manager=managerRepository.findManagerById(managerInfoDto.getId()).orElse(null);
        ResponseDto responseDto=ResponseDto.builder().build();
        responseDto.setCode(HttpStatus.SC_UNAUTHORIZED);

        if(manager!=null){
            manager.updateManager(UserInfo.builder()
                            .phone_num(managerInfoDto.getPhone_num())
                            .sex(managerInfoDto.getSex())
                            .name(managerInfoDto.getName())
                            .birthday(managerInfoDto.getBirthday())
                    .build());
            managerRepository.save(manager);
            responseDto.setCode(HttpStatus.SC_OK);
        }
        return responseDto;
    }

}
