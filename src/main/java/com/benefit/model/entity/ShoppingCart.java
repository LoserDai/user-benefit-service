package com.benefit.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
/**
 * @author Allen
 * @date 2025/6/10 16:22
 * 购物车表,里面包含多个CartItem
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_shopping_cart")
public class ShoppingCart implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    // 关联用户ID（唯一）
    private Long userId;

    // 创建时间
    private LocalDateTime createTime = LocalDateTime.now();

    // 更新时间
    private LocalDateTime updateTime = LocalDateTime.now();

    // 购物车项列表（非数据库字段）
    private List<CartItem> cartItems;

    // 选中商品总积分
    private BigDecimal totalSelectedPoints;
}
