package com.example.userservice.user.controller;

import com.example.userservice.common.ResponseDto;
import com.example.userservice.user.dto.ManagerCreateDto;
import com.example.userservice.user.service.BranchManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    //아이디 중복 확인
    @GetMapping("/bo/sign-up/{id}")
    public ResponseDto<Object> duplicateIdCheck(@PathVariable String id){
        System.out.println(id);
        return branchManagerService.duplicateIdCheck(id);
    }



}
