package com.example.userservice.user.client;

import com.example.userservice.common.IdListDto;
import com.example.userservice.common.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name="store-service", url="http://localhost:9000/store-service")
public interface StoreClient {

    @GetMapping("/store-service/manager/store-name/{id}")
     Map<String,Object> findStoreName(int id);

    @PostMapping("/store-service/manager/store-name")
    ResponseDto<Object> findStoreNames(@RequestBody IdListDto idListDto);

}
