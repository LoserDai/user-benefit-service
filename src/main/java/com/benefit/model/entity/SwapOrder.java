package com.benefit.model.entity;

/**
 * @author Allen
 * @date 2025/7/11 10:50
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
 * 积分兑换订单实体类
 * 对应表: T_SWAP_ORDER
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_swap_order")
public class SwapOrder {

    // 主键ID
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 订单编号
    private String orderId;

    // 客户编号
    private String customerId;

    // 兑换类型(B/P):Balance兑Points  (P/B):Points兑Balance
    private String type;

    // 买卖币种 (B/P)
    private String ccy;

    // 卖出金额
    private BigDecimal amountSell;

    // 买入金额
    private BigDecimal amountBuy;

    // 兑换手续费
    private BigDecimal exchangeFee;

    // 兑换率
    private BigDecimal exchangeRate;

    // 操作人
    private String operator;

    // 状态 (SUCCESS/FAILED)
    private String status;

    // 备注
    private String remark;

    // 创建时间
    private LocalDateTime createdAt;

    // 创建人
    private String createdBy;

    // 更新时间
    private LocalDateTime updatedAt;

    // 更新人
    private String updatedBy;

}
