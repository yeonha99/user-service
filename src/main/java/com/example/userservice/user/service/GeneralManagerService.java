package com.example.userservice.user.service;

import com.example.userservice.common.ResponseDto;
import com.example.userservice.user.domain.BranchManager;
import com.example.userservice.user.domain.Customer;
import com.example.userservice.user.domain.Manager;
import com.example.userservice.user.dto.ManagerInfoDto;
import com.example.userservice.user.repository.BranchManagerRepository;
import com.example.userservice.user.repository.CustomerRepository;
import com.example.userservice.user.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeneralManagerService {
    private final ManagerRepository managerRepository;
    private final BranchManagerRepository branchManagerRepository;



    public ResponseDto<Object> listApprovalRequests(){
        List<BranchManager> branchManagers=branchManagerRepository.findAllByApproval(false);
        List<ManagerInfoDto> preManager=new ArrayList<>();

        for (BranchManager branchManager : branchManagers) {
            preManager.add(ManagerInfoDto.builder()
                            .id(branchManager.getId())
                            .sex(branchManager.getUserInfo().getSex())
                            .store_name("store_name")
                            .name(branchManager.getUserInfo().getName())
                            .birthday(branchManager.getUserInfo().getBirthday())
                            .phone_num(branchManager.getUserInfo().getPhone_num())
                    .build());
        }

        if(branchManagers!=null) {
            return ResponseDto.builder().context(preManager).code(HttpStatus.SC_OK).build();
        }
        else return ResponseDto.builder().code(HttpStatus.SC_BAD_REQUEST).build();
    }
}
