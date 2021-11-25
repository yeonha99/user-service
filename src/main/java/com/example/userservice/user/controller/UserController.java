package com.example.userservice.user.controller;

import com.example.userservice.common.UserNameIdDto;
import com.example.userservice.user.service.CustomerService;
import com.example.userservice.user.service.ManagerService;
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

    @GetMapping("/user-info")//아이디에 따라 이름 가져오기ㅣ
    public UserNameIdDto userInfo(@RequestParam("id") String id){
        UserNameIdDto userNameIdDto=managerService.getNameId(id);
        if(userNameIdDto!=null){
            return userNameIdDto;
        }
        else{
            userNameIdDto=customerService.getNameId(id);
            if(userNameIdDto!=null)
                return userNameIdDto;
        }
        return null;
    }

}
