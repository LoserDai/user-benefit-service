package com.benefit.controller;

import com.benefit.common.BaseResponse;
import com.benefit.common.PageResult;
import com.benefit.common.ResultUtils;
import com.benefit.model.entity.PointTransaction;
import com.benefit.model.enums.ErrorCode;
import com.benefit.request.PointTransactionRequest;
import com.benefit.service.PointTransactionService;
import com.benefit.vo.PointTransactionVo;
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
 * @date 2025/7/18 16:58
 */

@RestController
@Slf4j
@RequestMapping("/pointTransaction")
@Api(tags = "积分交易流水")
public class PointTransactionController {

    @Resource
    private PointTransactionService pointTransactionService;

    @PostMapping("/savePointTrans")
    @ApiOperation("保存交易流水")
    public BaseResponse<Integer> savePointTrans(@RequestBody PointTransaction pointTransaction){
        if (pointTransaction.getUserId() == null || pointTransaction.getUserId() <= 0){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"userId not exist!");
        }

        log.info("changePoints is {}, changeBalance is {}", pointTransaction.getChangePoint(), pointTransaction.getChangeBalance());

        int count = pointTransactionService.savePointTrans(pointTransaction);
        return ResultUtils.success(count);
    }


    @PostMapping("/queryAllPointTransaction")
    @ApiOperation("分页查询交易流水")
    public BaseResponse<PageResult<PointTransactionVo>> queryAllPointTransaction(@RequestBody(required = false) PointTransactionRequest request) {

        // 参数校验
        if (request != null) {
            if (request.getPageNum() != null && request.getPageNum() < 1) {
                return new BaseResponse(ErrorCode.PAGE_ERROR);
            }
            if (request.getPageSize() != null && request.getPageSize() > 100) {
                return new BaseResponse(ErrorCode.PAGE_ERROR);
            }
        }

        PageResult<PointTransactionVo> result = pointTransactionService.queryAllPointTransaction(request);
        return ResultUtils.success(result);
    }
}
