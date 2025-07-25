package com.benefit.controller;

import com.benefit.common.BaseResponse;
import com.benefit.common.PageResult;
import com.benefit.common.ResultUtils;
import com.benefit.model.enums.ErrorCode;
import com.benefit.request.BenefitPackageRequest;
import com.benefit.service.BenefitPackageService;
import com.benefit.vo.BenefitPackageVo;
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
    public BaseResponse<PageResult<BenefitPackageVo>> queryPackage(@RequestBody BenefitPackageRequest request){

        PageResult<BenefitPackageVo> pageResult = benefitPackageService.queryPackage(request);

        return ResultUtils.success(pageResult);
    }


    /**
     * 新增权益包
     * @param request
     * @return
     */
    @PostMapping("/savePackage")
    @ApiOperation("新建权益包")
    public BaseResponse<Integer> savePackage (@RequestBody BenefitPackageRequest request){

        if (request == null){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"request param is null");
        }

        if (request.getPackageName() == null || request.getPackageName().isEmpty()){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"packageName can't be null");
        }

        if (request.getQuantity() == null || request.getQuantity() < 0){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"Quantity must great than 0!");
        }

        int count = benefitPackageService.savePackage(request);
        if (count < 0){
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"Save package failed!");
        }
        return ResultUtils.success(count);
    }

    @PostMapping("/updatePackage")
    @ApiOperation("更改权益包")
    public BaseResponse<Integer> updatePackage(@RequestBody BenefitPackageRequest request){

        if (request == null){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"request param is null");
        }

        if (request.getId() == null || request.getId() <= 0){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"No products specified");
        }

        if (request.getQuantity() == null || request.getQuantity() < 0){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"Quantity must great than 0!");
        }

        int updateCount = benefitPackageService.updatePackage(request);
        if (updateCount < 0){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"update package failed!");
        }
        return ResultUtils.success(updateCount);
    }
}
