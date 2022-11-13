package com.weiyang.gitegg.service.base.controller;


import com.weiyang.platform.boot.common.base.Result;
import com.weiyang.service.system.api.dto.ApiSystemDTO;
import com.weiyang.service.system.api.feign.ISystemFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "base")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Api(tags = "gitegg-base")
@RefreshScope
public class BaseController {

    private final ISystemFeign systemFeign;

    @GetMapping(value = "api/by/abc")
    @ApiOperation(value = "Fegin Get呼叫測試介面")
    public Result<Object> feginById(@RequestParam("id") Long id) {
        return Result.data(systemFeign.querySystemById(id));
    }

    @PostMapping(value = "api/by/dto")
    @ApiOperation(value = "Fegin Post呼叫測試介面")
    public Result<Object> feginByDto(@Valid @RequestBody ApiSystemDTO systemDTO) {
        return Result.data(systemFeign.querySystemByDto(systemDTO));
    }

}
