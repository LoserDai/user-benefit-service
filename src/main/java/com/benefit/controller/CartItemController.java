package com.benefit.controller;

import com.benefit.common.BaseResponse;
import com.benefit.common.ResultUtils;
import com.benefit.exception.BusinessException;
import com.benefit.model.entity.CartItem;
import com.benefit.model.entity.User;
import com.benefit.model.enums.ErrorCode;
import com.benefit.request.CartItemRequest;
import com.benefit.request.ShoppingCartRequest;
import com.benefit.service.CartItemService;
import com.benefit.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.Map;

import static com.benefit.constant.UserConstant.USER_LOGIN_STATUS;

/**
 * @Author Allen
 * @Date 2025/8/23 13:08
 * @Description
 */
@RestController
@RequestMapping("/cartItem")
@Api(tags = "操作购物车里的商品")
@Slf4j
public class CartItemController {

    @Resource
    private CartItemService cartItemService;


    @PostMapping("/deletedShoppingCart")
    @ApiOperation("删除购物车里的商品")
    public BaseResponse<Integer> deletedShoppingCart(HttpServletRequest httpServletRequest,@RequestBody Map<String, Integer> request){

        Integer id = request.get("id");
        Object userObj = httpServletRequest.getSession().getAttribute(USER_LOGIN_STATUS);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long userId = currentUser.getId();

        if (id == null){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"CartItemId can't be null!");
        }

        Integer count = cartItemService.deletedShoppingCart(userId,id);

        return ResultUtils.success(count);
    }
}
