package com.benefit.model.entity;

import com.benefit.model.enums.Status;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Allen
 * @date 2025/6/6 14:05
 */
@Entity
@Table(name = "benefit_package")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BenefitProduct implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 权益产品名称
    @Column(name = "product_name", length = 100, nullable = false, unique = true)
    private String productName;

    // 精度10位，小数2位（最大99999999.99）
    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "remark",nullable = false)
    private String remark;

    // 状态枚举
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Status status;

    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;
}
