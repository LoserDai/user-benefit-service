package com.benefit.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.benefit.model.enums.Status;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Allen
 * @date 2025/6/6 14:03
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BenefitPackage implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    // 权益包名称
    private String packageName;

    private List<BenefitProduct> products;

    // 权益包数量（库存）
    private Integer quantity = 0;

    // 精度10位，小数2位（最大99999999.99）
    private BigDecimal price;

    private String remark;

    // 状态枚举
    private Status status;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;
}
