package com.benefit.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Allen
 * @date 2025/6/10 16:21
 * 购物车项实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_cart_item")
public class CartItem implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    // 关联购物车ID
    private Long cartId;

    // 关联用户ID
    private Long userId;

    // 商品类型(1:权益产品 2:权益包 3:其他类型)
    private Integer itemType;

    // 商品ID
    private Long itemId;

    // 商品名称（冗余存储，避免频繁关联查询）
    private String itemName;

    // 商品图片
    private String itemImage;

    // 单价积分
    private BigDecimal pointPrice;

    // 数量
    private Integer quantity = 1;

    // 是否选中(0:未选 1:选中)
    private Boolean selected = true;

    // 创建时间
    private LocalDateTime createTime = LocalDateTime.now();

    // 更新时间
    private LocalDateTime updateTime = LocalDateTime.now();

    // 关联的权益对象（非数据库字段）
    private Object benefitItem;
}