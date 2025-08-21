package com.benefit.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.benefit.common.BaseResponse;
import com.benefit.common.ResultUtils;
import com.benefit.exception.BusinessException;
import com.benefit.model.entity.BenefitPoints;
import com.benefit.model.entity.SwapConfig;
import com.benefit.model.entity.SwapOrder;
import com.benefit.model.entity.User;
import com.benefit.model.enums.ErrorCode;
import com.benefit.service.BenefitPointsService;
import com.benefit.service.SwapConfigService;
import com.benefit.service.SwapOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

import static com.benefit.constant.UserConstant.USER_LOGIN_STATUS;

/**
 * @Author Allen
 * @Date 2025/8/21 16:54
 * @Description
 */
@RestController
@RequestMapping("/swapOrder")
@Api(tags = "积分兑换订单")
@Slf4j
public class SwapOrderController {

    @Resource
    private SwapOrderService swapOrderService;

    @Resource
    private BenefitPointsService benefitPointsService;


    @PostMapping("/saveSwapOrder")
    @ApiOperation("存储兑换订单")
    public BaseResponse<Integer> saveConfigById(@RequestBody SwapOrder order, HttpServletRequest request) {

        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATUS);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long userId = currentUser.getId();
        //看看余额是否足够用于兑换
        BenefitPoints serviceOne = benefitPointsService.getOne(new QueryWrapper<BenefitPoints>()
                .eq("user_id",userId));
        if ("B/P".equals(order.getCcy()) && (serviceOne.getBalance().compareTo(order.getAmountSell()) < 0)){
            log.info("user has {} Balance",serviceOne.getBalance());
            log.info("param is {} Balance",order.getAmountSell());
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"Has no enough Balance to Swap!");
        }
        if ("P/B".equals(order.getCcy()) && (serviceOne.getPoints().compareTo(order.getAmountSell()) < 0)){
            log.info("user has {} Points",serviceOne.getPoints());
            log.info("param is {} Points",order.getAmountSell());
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"Has no enough Points to Swap!");
        }

        int count = swapOrderService.saveSwapOrder(order,userId);
        if (count <= 0 ){
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"save swapOrder failed!");
        }
        return ResultUtils.success(count);
    }
}
