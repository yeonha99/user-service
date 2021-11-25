package com.example.userservice.user.controller;

import com.example.userservice.common.ResponseDto;
import com.example.userservice.user.service.CustomerService;
import com.example.userservice.user.service.GeneralManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user-service")
@RequiredArgsConstructor
public class GeneralManagerController {
    private final GeneralManagerService generalManagerService;

    @GetMapping("/pre-manager") //가입 승인 안받은 관리자들 목록
    public ResponseDto<Object> pre_manager_list(){
        return generalManagerService.listApprovalRequests();
    }



}
