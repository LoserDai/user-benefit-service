package com.benefit.controller;

import com.benefit.common.BaseResponse;
import com.benefit.service.BenefitPackageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Allen
 * @date 2025/7/4 10:51
 */

@RestController
@RequestMapping("/package")
@Api(tags = "权益包操作入口")
@Slf4j
public class BenefitPackageController {

    @Resource
    private BenefitPackageService benefitPackageService;

    @PostMapping("/queryPackage")
    @ApiOperation("查询权益包")
    public BaseResponse queryPackage(){
        return null;
    }
}
