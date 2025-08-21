package com.benefit.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Allen
 * @date 2025/6/6 14:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BenefitPoints implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    // 关联用户实体
    private Long userId;

    // 积分数量，默认值0
    private BigDecimal points;

    // 余额，默认值0
    private BigDecimal balance;
}
