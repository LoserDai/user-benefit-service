package com.benefit.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.benefit.common.BaseResponse;
import com.benefit.common.PageResult;
import com.benefit.common.ResultUtils;
import com.benefit.exception.BusinessException;
import com.benefit.model.entity.User;
import com.benefit.model.enums.ErrorCode;
import com.benefit.request.CancelOrderRequest;
import com.benefit.request.OrderMainRequest;
import com.benefit.service.OrderMainService;
import com.benefit.service.UserService;
import com.benefit.vo.OrderDashVo;
import com.benefit.vo.OrderMainVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.benefit.constant.UserConstant.USER_LOGIN_STATUS;

/**
 * @Author Allen
 * @Date 2025/8/20 10:05
 * @Description
 */

@RestController
@RequestMapping("/orderMain")
@Api(tags = "订单接口")
@Slf4j
public class OrderMainController {

    @Resource
    private OrderMainService orderMainService;

    @Resource
    private UserService userService;


    @PostMapping("/createOrderMain")
    @ApiOperation("创建订单")
    public BaseResponse<Integer> createOrderMain(HttpServletRequest httpServletRequest , @Param("addressId") long addressId) {

        Object userObj = httpServletRequest.getSession().getAttribute(USER_LOGIN_STATUS);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        if (ObjectUtils.isNull(addressId)){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"address can't be null");
        }
        //获取用户id
        long userId = currentUser.getId();
        int count = orderMainService.createOrderMain(userId,addressId);
        if (count <= 0){
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"create OrderMain failed!");
        }
        return ResultUtils.success(count);
    }

    @PostMapping("/cancelOrderMain")
    @ApiOperation("取消订单")
    public BaseResponse<Integer> cancelOrderMain(@RequestBody CancelOrderRequest request, HttpServletRequest httpServletRequest) {

        Object userObj = httpServletRequest.getSession().getAttribute(USER_LOGIN_STATUS);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        //获取用户id
        long userId = currentUser.getId();
        request.setUserId(userId);

        int count = orderMainService.cancelOrderMain(request);
        if (count <= 0){
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"cancel OrderMain failed!");
        }
        return ResultUtils.success(count);
    }

    @PostMapping("/payOrderMain")
    @ApiOperation("支付订单")
    public BaseResponse<Integer> payOrderMain(HttpServletRequest httpServletRequest, @Param("orderNo") String orderNo) {

        Object userObj = httpServletRequest.getSession().getAttribute(USER_LOGIN_STATUS);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        //获取用户id
        long userId = currentUser.getId();
        int count = orderMainService.payOrderMain(userId,orderNo);
        if (count <= 0){
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"pay OrderMain failed!");
        }
        return ResultUtils.success(count);
    }


    @PostMapping("/queryOrderMain")
    @ApiOperation("分页查询订单")
    public BaseResponse<PageResult<OrderMainVo>> queryOrderMain(@RequestBody OrderMainRequest request) {
        PageResult<OrderMainVo> pageResult = orderMainService.queryOrderMain(request);
        return ResultUtils.success(pageResult);
    }

    @ApiOperation("orderDashboard")
    @GetMapping("/getOrderCount")
    public BaseResponse<List<OrderDashVo>> getOrderCount(HttpServletRequest request) {

        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }

        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATUS);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        List<OrderDashVo> vo = orderMainService.getOrderCount();

        return ResultUtils.success(vo);
    }
}
