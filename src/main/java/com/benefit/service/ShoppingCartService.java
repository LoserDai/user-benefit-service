package com.benefit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.benefit.model.entity.ShoppingCart;
import com.benefit.request.ShoppingCartRequest;
import com.benefit.vo.ShoppingCartVo;

import java.util.List;
import java.util.Map;

/**
 * @Author Allen
 * @Date 2025/8/15 17:19
 * @Description
 */
public interface ShoppingCartService extends IService<ShoppingCart> {
    Integer createShoppingCart(ShoppingCartRequest request);

    List<Map<String, Object>> showShoppingCart(long userId);

    Integer updateShoppingCart(ShoppingCartRequest request);

    Integer clearShoppingCart(long userId);
}
