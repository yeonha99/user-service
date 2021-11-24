package com.example.userservice.user.service;

import com.example.userservice.Jwt.JwtServiceImpl;
import com.example.userservice.common.LoginDto;
import com.example.userservice.common.PwUpdateDto;
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
        String role= (String) user.get("role");

        if (role=="BN"){//지점 관리일 경우

           // Map<String,Object> map=client.findStoreName(branchManager.getStoreId()); //관리자의 매장 이름 매장 서비스에 물어봐서 가져오기
            //String store_name= ((String) map.get("store_name"));
            BranchManager branchManager =branchManagerRepository.findBranchManagerById((String) user.get("id")).orElse(null);

            if(branchManager!=null) {
                responseDto.setContext(ManagerInfoDto.builder()
                        .birthday(branchManager.getUserInfo().getBirthday())
                        .sex(branchManager.getUserInfo().getSex())
                        .name(branchManager.getUserInfo().getName())
                        .phone_num(branchManager.getUserInfo().getPhone_num())
                        .id(branchManager.getId())
                        .store_name("store_name")
                        .build()
                );
            }


        }else if(role=="GM"){

            //총관리자인 경우
            GeneralManager generalManager =generalManagerRepository.findGeneralManagerById((String) user.get("id")).orElse(null);

            if(generalManager!=null) {
                responseDto.setContext(ManagerInfoDto.builder()
                        .birthday(generalManager.getUserInfo().getBirthday())
                        .sex(generalManager.getUserInfo().getSex())
                        .name(generalManager.getUserInfo().getName())
                        .phone_num(generalManager.getUserInfo().getPhone_num())
                        .id(generalManager.getId())
                        .store_name(null)
                        .build());
            }
       }
        return responseDto;
    }

    //내 정보 수정
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


    //관리자 비밀번호 변경 기능
    public ResponseDto<Object> updatePw(String jwt, PwUpdateDto pwUpdateDto){
        Map<String, Object> objectMap=jwtService.getInfo(jwt);
        ResponseDto responseDto=ResponseDto.builder().build();
        responseDto.setCode(HttpStatus.SC_OK);
        Map<String,Object> user = (Map<String, Object>) objectMap.get("user");
        Manager manager=managerRepository.findManagerById((String) user.get("id")).orElse(null);
        //토큰 속 사람의 정보

        if(manager!=null&&passwordEncoder.matches(pwUpdateDto.getPrev_pw(), manager.getPw())) {
            //토큰 속 사람의 이전 비밀번호와 폼에서 보낸 이전 비밀번호가 같을 시에만 변경 로직 돌아가게 설정함
            manager.updatePw(passwordEncoder.encode(pwUpdateDto.getNew_pw())); //변경 할때도 암호화 ^_^

            managerRepository.save(manager);
            responseDto.setCode(HttpStatus.SC_OK);
        }
        return responseDto;
    }



}
