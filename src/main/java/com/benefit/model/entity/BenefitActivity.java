package com.benefit.model.entity;

import com.benefit.model.enums.ActivityStatus;
import com.benefit.model.enums.ActivityType;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;


/**
 * @author Allen
 * @date 2025/6/6 14:23
 */
@Entity
@Table(name = "benefit_activity")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BenefitActivity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 活动名称
    @Column(name = "activity_name", length = 100, nullable = false)
    private String activityName;

    // 活动描述
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // 活动类型
    @Enumerated(EnumType.STRING)
    @Column(name = "activity_type", length = 50, nullable = false)
    private ActivityType activityType;

    // 活动状态
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private ActivityStatus status;

    // 活动开始时间
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    // 活动结束时间
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    // 折扣值（0.8表示8折，20表示减20元）
    @Column(name = "discount_value", precision = 10, scale = 2)
    private BigDecimal discountValue;

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;  // 精度10位，小数2位（最大99999999.99）

    // 最低购买限制（可选）
    @Column(name = "min_purchase")
    private Integer minPurchase;

    // 限购数量（可选）
    @Column(name = "purchase_limit")
    private Integer purchaseLimit;

    // 关联的权益包
    @ManyToMany
    @JoinTable(
            name = "activity_package_rel",
            joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "package_id")
    )
    private Set<BenefitPackage> packages;

    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;
}
