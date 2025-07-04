package com.benefit.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.benefit.common.BaseResponse;
import com.benefit.common.PageResult;
import com.benefit.common.ResultUtils;
import com.benefit.exception.BusinessException;
import com.benefit.model.entity.BenefitProduct;
import com.benefit.model.enums.ErrorCode;
import com.benefit.request.BenefitProductRequest;
import com.benefit.service.BenefitProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Allen
 * @date 2025/7/4 10:44
 */
@RestController
@RequestMapping("/product")
@Api(tags = "权益产品操作入口")
@Slf4j
public class BenefitProductController {

    @Resource
    private BenefitProductService benefitProductService;

    @ApiOperation("新增产品接口")
    @PostMapping("/insertProduct")
    public BaseResponse insertProduct(@RequestBody BenefitProductRequest productRequest) {

        Boolean isExist = benefitProductService.isExistProduct(productRequest);
        if (isExist){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"This Product is isExist.");
        }
        Long isInsert = benefitProductService.insertProduct(productRequest);

        return ResultUtils.success(isInsert);
    }


    @ApiOperation("查询产品接口")
    @PostMapping("/queryAllProduct")
    public BaseResponse<PageResult<BenefitProduct>> queryAllProduct(@RequestBody(required = false) BenefitProductRequest request) {


        // 参数校验
        if (request != null) {
            if (request.getPageNum() != null && request.getPageNum() < 1) {
                return new BaseResponse(ErrorCode.PAGE_ERROR);
            }
            if (request.getPageSize() != null && request.getPageSize() > 100) {
                return new BaseResponse(ErrorCode.PAGE_ERROR);
            }
        }
        PageResult<BenefitProduct> result = benefitProductService.queryAllProduct(request);
        return ResultUtils.success(result);
    }
}
