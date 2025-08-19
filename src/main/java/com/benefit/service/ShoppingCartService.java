package com.benefit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.benefit.model.entity.ShoppingCart;
import com.benefit.request.ShoppingCartRequest;

/**
 * @Author Allen
 * @Date 2025/8/15 17:19
 * @Description
 */
public interface ShoppingCartService extends IService<ShoppingCart> {
    Integer createShoppingCart(ShoppingCartRequest request);

    ShoppingCart showShoppingCart(long userId);

    Integer updateShoppingCart(ShoppingCartRequest request);
}
