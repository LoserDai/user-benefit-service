package com.benefit.controller;

import com.benefit.common.BaseResponse;
import com.benefit.common.ResultUtils;
import com.benefit.model.entity.BenefitPoints;
import com.benefit.model.entity.SwapConfig;
import com.benefit.model.enums.ErrorCode;
import com.benefit.service.BenefitPointsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public BaseResponse rechargeById(@RequestBody BenefitPoints benefitPoints){

        Long userId = benefitPoints.getUserId();
        if (userId <0){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }

        int newPoints = benefitPointsService.rechargeById(benefitPoints);
        return ResultUtils.success(newPoints);
    }


}
