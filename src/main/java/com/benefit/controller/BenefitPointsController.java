package com.benefit.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.benefit.common.BaseResponse;
import com.benefit.common.ResultUtils;
import com.benefit.exception.BusinessException;
import com.benefit.model.entity.BenefitPoints;
import com.benefit.model.entity.SwapConfig;
import com.benefit.model.entity.User;
import com.benefit.model.enums.ErrorCode;
import com.benefit.request.BenefitPointsRequest;
import com.benefit.service.BenefitPointsService;
import com.benefit.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

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

    @Resource
    private UserService userService;


    @PostMapping("/query")
    @ApiOperation("查询接口")
    public BaseResponse queryPointsById(HttpServletRequest request){

        User loginUser = userService.getLoginUser(request);
        if (ObjectUtils.isEmpty(loginUser)){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        BenefitPoints serviceOne = benefitPointsService.getOne(new QueryWrapper<BenefitPoints>()
                .eq("user_id", loginUser.getId()));

        if (ObjectUtils.isEmpty(serviceOne)){
            log.info("no this user,{} ",loginUser.getId());
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(serviceOne);
    }

    @PostMapping("/modifyBalance")
    @ApiOperation("调账")
    public BaseResponse modifyBalance(@RequestBody BenefitPointsRequest request){

        Long userId = request.getUserId();
        if (userId <0){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        int points = benefitPointsService.modifyBalance(request);
        if (points <= 0){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(points);
    }


}
