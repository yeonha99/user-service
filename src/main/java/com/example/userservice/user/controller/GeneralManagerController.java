package com.example.userservice.user.controller;

import com.example.userservice.common.IdDto;
import com.example.userservice.common.ResponseDto;
import com.example.userservice.user.service.GeneralManagerService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/user-service")
@RequiredArgsConstructor
public class GeneralManagerController {
    private final GeneralManagerService generalManagerService;
    @ApiOperation("가입 승인 안받은 관리자들 목록")
    @GetMapping("/pre-manager") //가입 승인 안받은 관리자들 목록
    public ResponseDto<Object> preManagerList(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return generalManagerService.listApprovalRequests(pageable);
    }

    @ApiOperation("가입 승인하기")
    @PutMapping("/pre-manager")//가입 승인하기
    public ResponseDto<Object> preManagerApproval(@RequestBody IdDto idDto){

        return generalManagerService.approvalManager(idDto.getId());
    }

    @ApiOperation("가입 승인 불통")
    @DeleteMapping("/pre-manager")//가입 승인 불통
    public ResponseDto<Object> preManagerDelete(@RequestParam("id") String id){

        return generalManagerService.deleteManager(id);
    }

    @ApiOperation("관리자들 목록")
    @GetMapping("/manager") //가입 승인 처리된 관리자들 목록
    public ResponseDto<Object> managerList(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return generalManagerService.managerList(pageable);
    }


    @ApiOperation("관리자 삭제")
    @DeleteMapping("/manager")//관리자 삭제
    public ResponseDto<Object> managerDelete(@RequestParam("id") String id){
        return generalManagerService.deleteManager(id);
    }


}
