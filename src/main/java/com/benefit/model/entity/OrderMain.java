package com.benefit.model.entity;


import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.benefit.model.enums.OrderStatus;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Allen
 * @date 2025/6/10 16:24
 * 订单主表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderMain implements Serializable {

    //订单号(规则: ORD+yyMMdd+8位序列)'
    private String orderId;

    private Long userId;

    //订单总积分
    private Integer totalPoint;

    //状态(0:待支付 1:已支付 2:已发货 3:已完成 4:已取消)
    private OrderStatus status;

    private LocalDateTime payTime;

    private LocalDateTime deliveryTime;

    private LocalDateTime completeTime;

    private String cancelReason;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    // 订单明细
    private List<OrderItem> orderItems;
}
