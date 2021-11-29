package com.example.userservice.user.service;

import com.example.userservice.common.ResponseDto;
import com.example.userservice.user.client.StoreClient;
import com.example.userservice.user.domain.BranchManager;
import com.example.userservice.user.dto.ManagerInfoDto;
import com.example.userservice.user.repository.BranchManagerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GeneralManagerService {
    private final BranchManagerRepository branchManagerRepository;
    private final StoreClient storeClient;



    public ResponseDto<Object> listApprovalRequests(){ //가입 승인 요청한 관리자들 목록
        List<BranchManager> branchManagers=branchManagerRepository.findAllByApproval(false);
        List<ManagerInfoDto> preManager=new ArrayList<>();

        for (BranchManager branchManager : branchManagers) {

            String storeName= (String) storeClient.findStoreName(branchManager.getStoreId()).get("store_name");

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
            return ResponseDto.builder().context(preManager).code(HttpStatus.SC_OK).build();
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

    public ResponseDto<Object> managerList(){ // 모든 관리자 목록 조회
        List<BranchManager> branchManagers=branchManagerRepository.findAllByApproval(true);
        List<ManagerInfoDto> preManager=new ArrayList<>();

        for (BranchManager branchManager : branchManagers) {
            String storeName= (String) storeClient.findStoreName(branchManager.getStoreId()).get("store_name");

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
            return ResponseDto.builder().context(preManager).code(HttpStatus.SC_OK).build();
        }
        else return ResponseDto.builder().code(HttpStatus.SC_BAD_REQUEST).build();
    }

}
