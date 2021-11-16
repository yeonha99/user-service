package com.example.userservice;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ApiOperation("테스트용 컨트롤러")
@RequestMapping("/user-service")
public class TestController {

    @ResponseBody
    @GetMapping("/")
    @ApiOperation("서버가 잘 떴는지 확인하기용")
    public String home(){
        return "서버 잘떴습니다.";
    }

    @ResponseBody
    @CrossOrigin
    @GetMapping("/hello")
    @ApiOperation("restapi 명세 잘 되나 확인용")
    public String hello() {
        return "hello";
    }



}
