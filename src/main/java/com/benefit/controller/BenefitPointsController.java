package com.benefit.controller;

import com.benefit.common.BaseResponse;
import com.benefit.service.BenefitPointsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Allen
 * @date 2025/7/4 10:59
 */
@RestController
@RequestMapping("/points")
@Api(tags = "权益积分操作入口")
@Slf4j
public class BenefitPointsController {

    @Resource
    private BenefitPointsService benefitPointsService;


    @PostMapping("/recharge")
    @ApiOperation("充值接口")
    public BaseResponse rechargeById(HttpServletRequest request){
        return null;
    }
}
