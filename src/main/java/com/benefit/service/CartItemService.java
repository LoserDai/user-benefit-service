package com.benefit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.benefit.model.entity.CartItem;
import com.benefit.request.CartItemRequest;
import com.benefit.request.ShoppingCartRequest;

/**
 * @Author Allen
 * @Date 2025/8/15 17:24
 * @Description
 */
public interface CartItemService extends IService<CartItem> {
    Integer deletedShoppingCart(long userId, Integer id);
}
