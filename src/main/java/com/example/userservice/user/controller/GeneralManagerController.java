package com.example.userservice.user.controller;

import com.example.userservice.common.ResponseDto;
import com.example.userservice.user.service.CustomerService;
import com.example.userservice.user.service.GeneralManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/pre-manager")//가입 승인하기
    public ResponseDto<Object> pre_manager_approval(@RequestParam("id") String id){
        return generalManagerService.ApprovalManager(id);
    }

    @DeleteMapping("/pre-manager")//가입 승인 불통
    public ResponseDto<Object> pre_manager_delete(@RequestParam("id") String id){
        return generalManagerService.NotApprovalManager(id);
    }



}
