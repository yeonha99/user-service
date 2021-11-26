package com.example.userservice.user.service;

import com.example.userservice.Jwt.JwtServiceImpl;
import com.example.userservice.common.*;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final BranchManagerRepository branchManagerRepository;
    private final GeneralManagerRepository generalManagerRepository;
    private final JwtServiceImpl jwtService;
    private final PasswordEncoder passwordEncoder;
    private final StoreClient storeClient;

    public ManagerTokenDto loginManager(LoginDto loginDto){//관리자 로그인

        BranchManager branchManager=branchManagerRepository.findByIdAndApproval(loginDto.getId(),true).orElse(null);

        if(branchManager!=null) {
            if (passwordEncoder.matches(loginDto.getPw(), branchManager.getPw())) {
                //아이디 비번 일치 하면 일반 관리자 구분
                System.out.println("지점 관리자 비밀번호 일치 함");
                String storeName=(String) storeClient.findStoreName(branchManager.getStoreId()).get("storeName");
                return ManagerTokenDto.builder()
                        .storeId(branchManager.getStoreId())
                        .token(jwtService.createToken(UserDto.builder().id(branchManager.getId()).role("BM").build()))
                        .role("BM")
                        .storeName(storeName)
                        .build();

            }
        }

        GeneralManager generalManager=generalManagerRepository.findById(loginDto.getId()).orElse(null);

        if(generalManager!=null){
            if(passwordEncoder.matches(loginDto.getPw(), generalManager.getPw())){
                //총 관리자 구분
                System.out.println("총 관리자 비밀번호 일치 함");

                return ManagerTokenDto.builder()
                        .token(jwtService.createToken(UserDto.builder().id(generalManager.getId()).role("GM").build()))
                        .role("GM")
                        .storeId(0)
                        .storeName("총 관리자")
                        .build();

            }
        }
        return null;
    }


    //토큰 해석하고 관리자 정보 보내주는 기능
    public ResponseDto<Object> getMyInfo(String jwt){

        Map<String, Object> objectMap=jwtService.getInfo(jwt);
        ResponseDto responseDto=ResponseDto.builder().build();
        responseDto.setCode(HttpStatus.SC_OK);
        Map<String,Object> user = (Map<String, Object>) objectMap.get("user");
        String role= (String) user.get("role");
        System.out.println(role);
        if (role.equals("BM")){//지점 관리일 경우
            System.out.println("이프문 들어옴");

            BranchManager branchManager =branchManagerRepository.findById((String) user.get("id")).orElse(null);
            System.out.println(branchManager.getClass());
            if(branchManager!=null) {

                responseDto.setContext(ManagerInfoDto.builder()
                        .birthday(branchManager.getUserInfo().getBirthday())
                        .sex(branchManager.getUserInfo().getSex())
                        .name(branchManager.getUserInfo().getName())
                        .phoneNum(branchManager.getUserInfo().getPhone_num())
                        .id(branchManager.getId())
                        .build()
                );
            }


        }else if(role.equals("GM")){
            //총관리자인 경우
            GeneralManager generalManager =generalManagerRepository.findById((String) user.get("id")).orElse(null);

            if(generalManager!=null) {
                responseDto.setContext(ManagerInfoDto.builder()
                        .birthday(generalManager.getUserInfo().getBirthday())
                        .sex(generalManager.getUserInfo().getSex())
                        .name(generalManager.getUserInfo().getName())
                        .phoneNum(generalManager.getUserInfo().getPhone_num())
                        .id(generalManager.getId())
                        .build());
            }
       }
        return responseDto;
    }

    //내 정보 수정
    @Transactional
    public ResponseDto<Object> updateMyInfo(ManagerInfoDto managerInfoDto) {
        Manager manager=managerRepository.findById(managerInfoDto.getId()).orElse(null);
        ResponseDto responseDto=ResponseDto.builder().build();
        responseDto.setCode(HttpStatus.SC_UNAUTHORIZED);

        if(manager!=null){
            manager.updateManager(UserInfo.builder()
                            .phone_num(managerInfoDto.getPhoneNum())
                            .sex(managerInfoDto.getSex())
                            .name(managerInfoDto.getName())
                            .birthday(managerInfoDto.getBirthday())
                    .build());

            responseDto.setCode(HttpStatus.SC_OK);
        }
        return responseDto;
    }


    //관리자 비밀번호 변경 기능
    @Transactional
    public ResponseDto<Object> updatePw(String jwt, PwUpdateDto pwUpdateDto){
        Map<String, Object> objectMap=jwtService.getInfo(jwt);
        ResponseDto responseDto=ResponseDto.builder().build();
        responseDto.setCode(HttpStatus.SC_OK);
        Map<String,Object> user = (Map<String, Object>) objectMap.get("user");
        Manager manager=managerRepository.findById((String) user.get("id")).orElse(null);
        //토큰 속 사람의 정보

        if(manager!=null&&passwordEncoder.matches(pwUpdateDto.getPrevPw(), manager.getPw())) {
            //토큰 속 사람의 이전 비밀번호와 폼에서 보낸 이전 비밀번호가 같을 시에만 변경 로직 돌아가게 설정함

            manager.updatePw(passwordEncoder.encode(pwUpdateDto.getNewPw())); //변경 할때도 암호화 ^_^

        }
        return responseDto;
    }


    public ManagerInfoDto getInfoById(String id) {
        Manager manager= managerRepository.getById(id);
        if(manager!=null){

            return ManagerInfoDto.builder()
                    .phoneNum(manager.getUserInfo().getPhone_num())
                    .birthday(manager.getUserInfo().getBirthday())
                    .id(manager.getId())
                    .name(manager.getUserInfo().getName())
                    .sex(manager.getUserInfo().getSex())
                    .build();
        }
        return null;
    }
}
