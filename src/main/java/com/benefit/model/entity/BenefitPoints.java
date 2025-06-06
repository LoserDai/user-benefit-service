package com.benefit.model.entity;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Allen
 * @date 2025/6/6 14:18
 */
@Entity
@Table(name = "benefit_points")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BenefitPoints implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 关联用户实体
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    private User user;

    // 积分数量，默认值0
    @Column(name = "points", nullable = false)
    private Integer points = 0;

    // 余额，默认值0
    @Column(name = "balance", precision = 15, scale = 2, nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;
}
