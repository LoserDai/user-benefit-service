package com.benefit.controller;

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
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;

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
    @PostMapping(value = "/insertProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse insertProduct(
            @RequestParam("file") MultipartFile file,
            @RequestPart("productRequest") @Valid BenefitProductRequest productRequest) {


        Boolean isExist = benefitProductService.isExistProduct(productRequest);
        if (isExist) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "This Product is isExist.");
        }
        Long isInsert = benefitProductService.insertProduct(productRequest, file);

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


    @ApiOperation("下架产品接口")
    @PostMapping("/deleteProduct")
    public BaseResponse deleteProduct(@RequestParam String productId){

        if (StringUtils.isBlank(productId)){
            return new BaseResponse(ErrorCode.PARAMS_ERROR);
        }
        int count = benefitProductService.deleteProduct(productId);
        if (count<=0){
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR);
        }
            return ResultUtils.success(count);
    }

    @ApiOperation("编辑产品接口")
    @PostMapping(value = "/updateProduct",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse updateProduct(@RequestPart("product") BenefitProduct product,
                                      @RequestParam("file") MultipartFile file){
        if (StringUtils.isBlank(product.getId().toString())){
            return new BaseResponse(ErrorCode.PARAMS_ERROR);
        }
        BenefitProduct isExist = benefitProductService.getById(product.getId());
        if (ObjectUtils.isEmpty(isExist)){
            log.info("product is not exist!");
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        product = benefitProductService.updateProduct(product, file);

        return ResultUtils.success(product);
    }
}
