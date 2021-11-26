package com.example.userservice.user.client;

import com.example.userservice.common.ResponseDto;
import com.example.userservice.common.StoreDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

@FeignClient(name="store-service")
public interface StoreClient {

    @GetMapping("/manager/store-name")//매장 id에 따른 이름 하나만 받아오기
    Map<String,Object> findStoreName(@RequestParam int storeId);

}
