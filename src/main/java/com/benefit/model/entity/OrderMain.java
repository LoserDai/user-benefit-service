package com.benefit.model.entity;


import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.benefit.model.enums.OrderStatus;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Allen
 * @date 2025/6/10 16:24
 * 订单主表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderMain implements Serializable {

    //订单号(规则: ORD+yyMMdd+8位序列)'
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "user_id")
    private Long userId;

    //订单总积分
    @Column(name = "total_point")
    private Integer totalPoint;

    //状态(0:待支付 1:已支付 2:已发货 3:已完成 4:已取消)
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private OrderStatus status;

    @Column(name = "pay_time", nullable = false)
    private LocalDateTime payTime;

    @Column(name = "delivery_time", nullable = false)
    private LocalDateTime deliveryTime;

    @Column(name = "complete_time", nullable = false)
    private LocalDateTime completeTime;

    @Column(name = "cancel_reason", nullable = false)
    private String cancelReason;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    // 订单明细
    private List<OrderItem> orderItems;
}
