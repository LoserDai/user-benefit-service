package com.benefit.controller;


import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.benefit.common.BaseResponse;
import com.benefit.common.ResultUtils;
import com.benefit.exception.BusinessException;
import com.benefit.model.entity.ShoppingCart;
import com.benefit.model.entity.User;
import com.benefit.model.enums.ErrorCode;
import com.benefit.request.ShoppingCartRequest;
import com.benefit.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.benefit.constant.UserConstant.USER_LOGIN_STATUS;

/**
 * @Author Allen
 * @Date 2025/8/15 17:14
 * @Description
 */
@RestController
@RequestMapping("/shoppingCart")
@Api(tags = "操作购物车")
@Slf4j
public class ShoppingCartController {


    @Resource
    private ShoppingCartService shoppingCartService;


    @PostMapping("/createShoppingCart")
    @ApiOperation("创建购物车")
    public BaseResponse<Integer> createShoppingCart(@RequestBody ShoppingCartRequest request, HttpServletRequest httpServletRequest){


        Object userObj = httpServletRequest.getSession().getAttribute(USER_LOGIN_STATUS);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long userId = currentUser.getId();

        if (request.getTotalSelectedPoints() == null || request.getCartItems().size() <= 0){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"params can't be null!");
        }
        request.setUserId(userId);
        Integer count = shoppingCartService.createShoppingCart(request);
        if (count <= 0){
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"create shoppingCart failed!");
        }
        return ResultUtils.success(count);
    }


    @PostMapping("/showShoppingCart")
    @ApiOperation("查看购物车")
    public BaseResponse<ShoppingCart> showShoppingCart(HttpServletRequest request){

        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATUS);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long userId = currentUser.getId();

        ShoppingCart shoppingCart = shoppingCartService.showShoppingCart(userId);
        if (ObjectUtils.isNull(shoppingCart)) {
            return null;
        }
        return ResultUtils.success(shoppingCart);
    }

    @PostMapping("/updateShoppingCart")
    @ApiOperation("修改购物车")
    public BaseResponse<Integer> updateShoppingCart(HttpServletRequest httpServletRequest, @RequestBody ShoppingCartRequest request){

        Object userObj = httpServletRequest.getSession().getAttribute(USER_LOGIN_STATUS);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long userId = currentUser.getId();
        request.setUserId(userId);
        Integer count = shoppingCartService.updateShoppingCart(request);

        return ResultUtils.success(count);
    }
}
