package com.example.userservice.user.controller;

import com.example.userservice.common.ResponseDto;
import com.example.userservice.user.dto.ManagerCreateDto;
import com.example.userservice.user.service.BranchManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-service")
@RequiredArgsConstructor
public class BranchManagerController {
    private final BranchManagerService branchManagerService;

    //관리자 가입
    @PostMapping("/bo/sign-up")
    public ResponseDto<Object> createManager(@RequestBody ManagerCreateDto managerCreateDto){
        return branchManagerService.createManager(managerCreateDto);
    }

}
