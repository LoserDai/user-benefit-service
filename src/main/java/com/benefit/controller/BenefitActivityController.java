package com.benefit.controller;

import com.benefit.common.BaseResponse;
import com.benefit.common.ResultUtils;
import com.benefit.model.enums.ErrorCode;
import com.benefit.request.BenefitActivityRequest;
import com.benefit.service.BenefitActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Allen
 * @date 2025/7/4 10:53
 */
@RestController
@RequestMapping("/activity")
@Api(tags = "权益活动操作入口")
@Slf4j
public class BenefitActivityController {

    @Resource
    private BenefitActivityService benefitActivityService;

    @PostMapping("/saveActivity")
    @ApiOperation("新增权益活动")
    public BaseResponse<Integer> saveActivity(@RequestBody BenefitActivityRequest request){

        if (request.checkParamIsExist()){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"paramJson can't be null or ''");
        }

        int count = benefitActivityService.saveActivity(request);
        if (count < 0){
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"Save activity failed!");
        }

        return ResultUtils.success(count);
    }
}
