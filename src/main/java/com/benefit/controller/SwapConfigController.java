package com.benefit.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.benefit.common.BaseResponse;
import com.benefit.common.ResultUtils;
import com.benefit.model.entity.SwapConfig;
import com.benefit.model.enums.ErrorCode;
import com.benefit.service.SwapConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Allen
 * @date 2025/7/11 11:40
 */

@RestController
@RequestMapping("/config")
@Api(tags = "积分配置")
@Slf4j
public class SwapConfigController {

    @Resource
    private SwapConfigService swapConfigService;



    @GetMapping("/getConfig")
    @ApiOperation("获取兑换配置接口")
    public BaseResponse getConfigById(
            @RequestParam String userId,
            @RequestParam String ccy) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(ccy)) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }

        // 1. 尝试查询用户自定义配置
        SwapConfig config = swapConfigService.getOne(new QueryWrapper<SwapConfig>()
                .eq("user_id", userId)
                .eq("ccy", ccy));

        // 2. 如果不存在，查询系统默认配置 (user_id=0)
        if (ObjectUtils.isEmpty(config)) {
            config = swapConfigService.getOne(new QueryWrapper<SwapConfig>()
                    .eq("user_id", 0)
                    .eq("ccy", ccy));
        }

        // 3. 返回配置信息
        return ResultUtils.success(config);
    }
}
