package com.benefit.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.benefit.model.enums.ActivityStatus;
import com.benefit.model.enums.ActivityType;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


/**
 * @author Allen
 * @date 2025/6/6 14:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BenefitActivity implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    // 活动名称
    private String activityName;

    // 活动描述
    private String description;

    // 活动类型
    private ActivityType activityType;

    // 活动状态
    private ActivityStatus status;

    // 活动开始时间
    private LocalDateTime startTime;

    // 活动结束时间
    private LocalDateTime endTime;

    // 折扣值（0.8表示8折，20表示减20元）
    private BigDecimal discountValue;

    // 精度10位，小数2位（最大99999999.99）
    private BigDecimal price;

    private String remark;

    // 最低购买限制（可选）
    private Integer minPurchase;

    // 限购数量（可选）
    private Integer purchaseLimit;


    private List<BenefitPackage> packages;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
