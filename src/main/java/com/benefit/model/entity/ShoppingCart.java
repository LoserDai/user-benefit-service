package com.benefit.model.entity;


import lombok.*;

import java.io.Serializable;
import java.util.List;
/**
 * @author Allen
 * @date 2025/6/10 16:22
 * 购物车表
 */
@Data
@Builder
public class ShoppingCart implements Serializable {

    private Long userId;

    private List<CartItem> cartItems;

    // 选中商品总积分
    private Integer totalSelectedPoints;
}
