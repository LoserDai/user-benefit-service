package com.benefit.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Allen
 * @date 2025/6/10 16:25
 * 订单明细表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", length = 100, nullable = false)
    private String orderId;

    @Column(name = "item_type",  nullable = false)
    private Integer itemType;

    @Column(name = "item_id",  nullable = false)
    private Long itemId;

    @Column(name = "item_name",  nullable = false)
    private String itemName;

    @Column(name = "quantity",  nullable = false)
    private Integer quantity;

    @Column(name = "point_price",  nullable = false)
    private Integer pointPrice;

    @Column(name = "total_point",  nullable = false)
    private Integer totalPoint;
}
