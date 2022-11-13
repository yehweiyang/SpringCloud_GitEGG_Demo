package com.weiyang.gitegg.service.system.controller;

import com.weiyang.gitegg.service.system.dto.SystemDTO;
import com.weiyang.gitegg.service.system.service.ISystemService;
import com.weiyang.platform.boot.common.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "system")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Api(tags = "gitegg-system")
@RefreshScope
public class SystemController {

    private final ISystemService systemService;

    @Value("${spring.datasource.maxActive}")
    private String nacosMaxActiveType;

    @GetMapping(value = "list")
    @ApiOperation(value = "system list介面")
    public Object list() {
        return systemService.list();
    }


    @GetMapping(value = "page")
    @ApiOperation(value = "system page介面")
    public Object page() {
        return systemService.page();
    }

    @GetMapping(value = "exception")
    @ApiOperation(value = "自訂異常及返回測試介面")
    public Result<String> exception() {
        return Result.data(systemService.exception());
    }

    @GetMapping(value = "systemException")
    @ApiOperation(value = "自訂異常及返回測試介面")
    public Result<String> systemException() {
        return Result.data(systemService.systemException());
    }

    @PostMapping(value = "valid")
    @ApiOperation(value = "參數校驗測試介面")
    public Result<SystemDTO> valid(@Valid @RequestBody SystemDTO systemDTO) {
        return Result.data(systemDTO);
    }

    @PostMapping(value = "nacos")
    @ApiOperation(value = "Nacos讀取組態檔案測試介面")
    public Result<String> nacos() {
        return Result.data(nacosMaxActiveType);
    }

}
