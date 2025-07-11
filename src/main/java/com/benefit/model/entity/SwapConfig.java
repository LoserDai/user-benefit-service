package com.benefit.model.entity;

/**
 * @author Allen
 * @date 2025/7/11 10:51
 */
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 兑换配置实体类
 * 对应表: T_SWAP_CONFIG
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_swap_config")
public class SwapConfig {

    // 主键ID
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 用户ID
    private Long userId;

    // 手续费率
    private BigDecimal fee;

    // 兑换率
    private BigDecimal rate;

    // 买卖币种 (B/P)
    private String ccy;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;

    // 创建人
    private String createBy;

    // 更新人
    private String updateBy;
}
