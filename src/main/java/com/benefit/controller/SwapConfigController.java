package com.benefit.controller;

import com.benefit.common.BaseResponse;
import com.benefit.model.entity.SwapConfig;
import com.benefit.service.SwapConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Allen
 * @date 2025/7/11 11:40
 */

@RestController
@RequestMapping("/config")
@Api(tags = "积分配置")
@Slf4j
public class SwapConfigController {

//    @Resource
//    private SwapConfigService swapConfigService;



    @GetMapping("/getConfig")
    @ApiOperation("获取兑换配置接口")
    public BaseResponse getConfigById(@RequestParam String customerId, @RequestParam String ccy){

        //该用户是否有配置自己的费率?

        //有就查自己的,没有就用默认的
        SwapConfig config = new SwapConfig();
        return null;
    }
}
