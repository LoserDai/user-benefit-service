package com.benefit.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author Allen
 * @Date 2025/8/23 13:39
 * @Description
 */
@Data
public class CartItemRequest extends PageBaseRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

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
    private Integer quantity;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;
}
