package com.benefit.model.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.List;
/**
 * @author Allen
 * @date 2025/6/10 16:22
 * 购物车表,里面包含多个CartItem
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//TODO 表没建
@TableName("t_shopping_cart")
public class ShoppingCart implements Serializable {

    private Long userId;

    private List<CartItem> cartItems;

    // 选中商品总积分
    private Integer totalSelectedPoints;
}
