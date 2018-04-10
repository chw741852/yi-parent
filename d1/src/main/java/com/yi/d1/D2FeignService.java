package com.yi.d1;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("d2")
public interface D2FeignService {
    @RequestMapping(method = RequestMethod.GET, value = "/index")
    String index();
}
