package com.benefit.controller;

import com.benefit.common.BaseResponse;
import com.benefit.common.PageResult;
import com.benefit.common.ResultUtils;
import com.benefit.model.enums.ErrorCode;
import com.benefit.request.UserAddressRequest;
import com.benefit.service.UserAddressService;
import com.benefit.vo.UserAddressVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author Allen
 * @Date 2025/8/18 9:43
 * @Description
 */

@RestController
@RequestMapping("/userAddress")
@Api(tags = "用户收货地址")
@Slf4j
public class UserAddressController {

    @Resource
    private UserAddressService userAddressService;

    @ApiOperation("新增收货地址")
    @PostMapping("/addAddress")
    public BaseResponse<Integer> addAddress(@RequestBody UserAddressRequest request) {

        if (request.checkParamIsExist()) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "Params can't be null !");
        }

        Integer count = userAddressService.addAddress(request);
        if (count <= 0) {
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "Add address fail!");
        }

        return ResultUtils.success(count);
    }

    @ApiOperation("删除收货地址")
    @PostMapping("/removeAddress")
    public BaseResponse<Integer> removeAddress(@RequestBody UserAddressRequest request) {

        if (request.getId() == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "Params can't be null !");
        }

        Integer count = userAddressService.removeAddress(request);
        if (count <= 0) {
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "Remove address fail!");
        }

        return ResultUtils.success(count);
    }

    @ApiOperation("修改收货地址")
    @PostMapping("/updateAddress")
    public BaseResponse<Integer> updateAddress(@RequestBody UserAddressRequest request) {

        if (request.getUserId() == null || request.getId() == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "Params can't be null !");
        }

        Integer count = userAddressService.updateAddress(request);
        if (count <= 0) {
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "update address fail!");
        }

        return ResultUtils.success(count);
    }

    @ApiOperation("查询用户地址接口")
    @PostMapping("/queryAllUserAddress")
    public BaseResponse<PageResult<UserAddressVo>> queryAllUserAddress(@RequestBody(required = false) UserAddressRequest request) {

        // 参数校验
        if (request != null) {
            if (request.getPageNum() != null && request.getPageNum() < 1) {
                return new BaseResponse(ErrorCode.PAGE_ERROR);
            }
            if (request.getPageSize() != null && request.getPageSize() > 100) {
                return new BaseResponse(ErrorCode.PAGE_ERROR);
            }
        }
        PageResult<UserAddressVo> result = userAddressService.queryAllUserAddress(request);
        return ResultUtils.success(result);
    }

}
