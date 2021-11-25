package com.example.userservice.user.controller;

import com.example.userservice.common.ResponseDto;
import com.example.userservice.user.service.GeneralManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;



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
        return generalManagerService.deleteManager(id);
    }

    @GetMapping("/manager") //가입 승인 처리된 관리자들 목록
    public ResponseDto<Object> manager_list(){
        return generalManagerService.ManagerList();
    }

    @DeleteMapping("/manager")//관리자 삭제
    public ResponseDto<Object> manager_delete(@RequestParam("id") String id){
        return generalManagerService.deleteManager(id);
    }


}
