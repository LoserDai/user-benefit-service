package com.benefit.model.entity;

import com.benefit.model.enums.Status;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Allen
 * @date 2025/6/6 14:03
 */

@Entity
@Table(name = "benefit_package")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BenefitPackage implements Serializable {
    @Id
    private Long id;

    // 权益包名称
    @Column(name = "package_name", length = 100, nullable = false)
    private String packageName;

    // 包含的权益产品
    @ManyToMany
    @JoinTable(
            name = "package_product_rel",  // 关联表名
            joinColumns = @JoinColumn(name = "package_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<BenefitProduct> products;

    // 权益包数量（库存）
    @Column(name = "quantity", nullable = false)
    @Builder.Default  // 确保Builder模式使用默认值
    private Integer quantity = 0;

    // 状态枚举
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Status status;

    // 创建时间
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;

    // 更新时间
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;
}
