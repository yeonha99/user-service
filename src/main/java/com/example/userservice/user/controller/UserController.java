package com.example.userservice.user.controller;

import com.example.userservice.user.dto.CustomerInfoDto;
import com.example.userservice.user.dto.ManagerInfoDto;
import com.example.userservice.user.service.CustomerService;
import com.example.userservice.user.service.ManagerService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-service")
@RequiredArgsConstructor
public class UserController {
    private final ManagerService managerService;
    private final CustomerService customerService;

    @ApiOperation("고객 아이디에 따라 정보 가져오기")
    @GetMapping("/customer-info")//고객 아이디에 따라 정보 가져오기ㅣ
    public CustomerInfoDto customerInfo(@RequestParam("id") String id){
            CustomerInfoDto customerInfoDto =customerService.getInfoById(id);
            if(customerInfoDto!=null)
                return customerInfoDto;

        return null;
    }

    @ApiOperation("관리자 아이디에 따라 정보 가져오기ㅣ")
    @GetMapping("/manager-info")//관리자 아이디에 따라 정보 가져오기ㅣ
    public ManagerInfoDto managerInfo(@RequestParam("id") String id){
        ManagerInfoDto managerInfoDto=managerService.getInfoById(id);
        if(managerInfoDto!=null){
            return managerInfoDto;
        }
        return null;
    }

}
