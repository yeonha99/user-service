package com.example.userservice.user.service;

import com.example.userservice.common.ResponseDto;
import com.example.userservice.user.client.StoreClient;
import com.example.userservice.user.domain.BranchManager;
import com.example.userservice.user.dto.ManagerInfoDto;
import com.example.userservice.user.repository.BranchManagerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GeneralManagerService {
    private final BranchManagerRepository branchManagerRepository;
    private final StoreClient storeClient;



    public ResponseDto<Object> listApprovalRequests(Pageable pageable){ //가입 승인 요청한 관리자들 목록
        Page<BranchManager> branchManagers=branchManagerRepository.findAllByApproval(false, pageable);

        List<ManagerInfoDto> preManager=new ArrayList<>();

        for (BranchManager branchManager : branchManagers) {

            Map<String,Object> objectMap= ((Map<String, Object>) storeClient.findStoreName(branchManager.getStoreId()).get("context"));
            String storeName=(String) objectMap.get("name");

            preManager.add(ManagerInfoDto.builder()
                            .id(branchManager.getId())
                            .sex(branchManager.getUserInfo().getSex())
                            .storeName(storeName)
                            .name(branchManager.getUserInfo().getName())
                            .birthday(branchManager.getUserInfo().getBirthday())
                            .phoneNum(branchManager.getUserInfo().getPhoneNum())
                    .build());
        }

        if(branchManagers!=null) {
            return ResponseDto.builder().context(preManager).code(HttpStatus.SC_OK).totalPage(branchManagers.getTotalPages()).build();
        }
        else return ResponseDto.builder().code(HttpStatus.SC_BAD_REQUEST).build();
    }

    @Transactional
    public ResponseDto<Object> approvalManager(String id){
        BranchManager branchManager=branchManagerRepository.findById(id).orElse(null);

        if(branchManager!=null){
            branchManager.approvalManager(); //가입 승인으로 변경
        }

        return ResponseDto.builder().code(HttpStatus.SC_OK).build();
    }

    @Transactional
    public ResponseDto<Object> deleteManager(String id){
        BranchManager branchManager=branchManagerRepository.findById(id).orElse(null);


        if(branchManager!=null){
            branchManagerRepository.delete(branchManager);//관리자 삭제
        }

        return ResponseDto.builder().code(HttpStatus.SC_OK).build();
    }

    public ResponseDto<Object> managerList(Pageable pageable){ // 모든 관리자 목록 조회
        Page <BranchManager> branchManagers=branchManagerRepository.findAllByApproval(true,pageable);
        List<ManagerInfoDto> preManager=new ArrayList<>();

        for (BranchManager branchManager : branchManagers) {
            Map<String,Object> objectMap= ((Map<String, Object>) storeClient.findStoreName(branchManager.getStoreId()).get("context"));
            String storeName=(String) objectMap.get("name");

            preManager.add(ManagerInfoDto.builder()
                    .id(branchManager.getId())
                    .sex(branchManager.getUserInfo().getSex())
                    .storeName(storeName)
                    .name(branchManager.getUserInfo().getName())
                    .birthday(branchManager.getUserInfo().getBirthday())
                    .phoneNum(branchManager.getUserInfo().getPhoneNum())
                    .build());
        }

        if(branchManagers!=null) {
            return ResponseDto.builder().context(preManager).code(HttpStatus.SC_OK).totalPage(branchManagers.getTotalPages()).build();
        }
        else return ResponseDto.builder().code(HttpStatus.SC_BAD_REQUEST).build();
    }

}
