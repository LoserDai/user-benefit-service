package com.benefit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.benefit.exception.BusinessException;
import com.benefit.mapper.CartItemMapper;
import com.benefit.model.entity.CartItem;
import com.benefit.model.enums.ErrorCode;
import com.benefit.request.CartItemRequest;
import com.benefit.service.CartItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author Allen
 * @Date 2025/8/23 13:13
 * @Description
 */
@Service
@Slf4j
public class CartItemServiceImpl  extends ServiceImpl<CartItemMapper, CartItem> implements CartItemService {


    @Resource
    private CartItemMapper cartItemMapper;


    @Override
    public Integer deletedShoppingCart(long userId, Integer id) {
        Integer count = cartItemMapper.deletedShoppingCart(userId, id);
        if (count <= 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"deleted ShoppingCart failed!");
        }
        return count;
    }
}
