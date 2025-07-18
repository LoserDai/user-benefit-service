package com.benefit.controller;

import com.benefit.common.BaseResponse;
import com.benefit.common.ResultUtils;
import com.benefit.service.PointTransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
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
    public BaseResponse savePointTrans(){
        return ResultUtils.success(null);
    }
}
