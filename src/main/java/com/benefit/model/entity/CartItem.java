package com.benefit.model.entity;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Allen
 * @date 2025/6/10 16:21
 * 购物车项实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", length = 100, nullable = false)
    private Long userId;


    @Column(name = "item_type", nullable = false)
    private Integer itemType;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "selected", nullable = false)
    private Boolean selected;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    // 关联的权益对象 可能是权益产品也有可能是权益包
    // BenefitProduct 或 BenefitPackage
    private Object benefitItem;
}