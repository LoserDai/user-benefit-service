package com.benefit.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.benefit.common.BaseResponse;
import com.benefit.common.PageResult;
import com.benefit.common.ResultUtils;
import com.benefit.exception.BusinessException;
import com.benefit.model.entity.BenefitActivity;
import com.benefit.model.enums.ActivityStatus;
import com.benefit.model.enums.ErrorCode;
import com.benefit.request.BenefitActivityRequest;
import com.benefit.service.BenefitActivityService;
import com.benefit.vo.BenefitActivityVo;
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

    @PostMapping("/updateActivity")
    @ApiOperation("修改权益活动")
    public BaseResponse<Integer> updateActivity(@RequestBody BenefitActivityRequest request){
        // 参数校验
        if (ObjectUtils.isNull(request)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if(ObjectUtils.isNull(request.getId()) || request.getId() <= 0){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"ActivityId is null");
        }

        if(ObjectUtils.isNull(request.getStatus())){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"ActivityStatus is null");
        }
        int count = benefitActivityService.updateActivity(request);
        if (count <= 0){
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"Update activity failed!");
        }
        return ResultUtils.success(count);
    }

    @PostMapping("/queryActivityList")
    @ApiOperation("分页查询权益活动")
    public BaseResponse<PageResult<BenefitActivityVo>> queryActivityList(@RequestBody BenefitActivityRequest request){

        PageResult<BenefitActivityVo> pageResult = benefitActivityService.queryActivityList(request);

        return ResultUtils.success(pageResult);
    }
}
