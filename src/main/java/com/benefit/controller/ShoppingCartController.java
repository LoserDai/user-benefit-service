package com.benefit.controller;


import com.benefit.common.BaseResponse;
import com.benefit.common.ResultUtils;
import com.benefit.model.entity.ShoppingCart;
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
    public BaseResponse<Integer> createShoppingCart(@RequestBody ShoppingCartRequest request){

        if (request.getUserId() == null || request.getTotalSelectedPoints() == null || request.getCartItems().size() <= 0){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"params can't be null!");
        }
        Integer count = shoppingCartService.createShoppingCart(request);
        if (count <= 0){
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"create shoppingCart failed!");
        }
        return ResultUtils.success(count);
    }
}
