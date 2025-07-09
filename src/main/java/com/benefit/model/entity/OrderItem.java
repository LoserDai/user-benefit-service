package com.benefit.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;


import java.io.Serializable;

/**
 * @author Allen
 * @date 2025/6/10 16:25
 * 订单明细表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String orderId;

    private Integer itemType;

    private Long itemId;

    private String itemName;

    private Integer quantity;

    private Integer pointPrice;

    private Integer totalPoint;
}
