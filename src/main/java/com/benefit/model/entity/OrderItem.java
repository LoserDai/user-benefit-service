package com.benefit.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;


import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Allen
 * @date 2025/6/10 16:25
 * 订单明细表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_order_item")
public class OrderItem implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long orderId;

    //商品类型(1:权益产品 2:权益包 3:其他类型)
    private Integer itemType;

    //商品ID
    private Long itemId;

    //商品名称
    private String itemName;

    //数量
    private Integer quantity;

    //单价
    private BigDecimal pointPrice;

    //总价
    private BigDecimal totalPoint;
}
