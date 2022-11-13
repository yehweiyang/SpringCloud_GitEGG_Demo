package com.weiyang.service.system.api.feign;

import com.weiyang.platform.boot.common.base.Result;
import com.weiyang.service.system.api.dto.ApiSystemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "gitegg-service-system")
public interface ISystemFeign {

    /**
     * OpenFeign測試Get
     *
     * @param id
     * @return
     */
    @GetMapping("/system/api/by/abc")
    Result<Object> querySystemById(@RequestParam("id") Long id);

    /**
     * OpenFeign測試Post
     *
     * @param apiSystemDTO
     * @return ApiSystemDTO
     */
    @PostMapping("/system/api/by/dto")
    Result<ApiSystemDTO> querySystemByDto(@RequestBody ApiSystemDTO apiSystemDTO);
}
