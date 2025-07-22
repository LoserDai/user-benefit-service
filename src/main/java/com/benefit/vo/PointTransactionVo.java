package com.benefit.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Allen
 * @date 2025/7/18 17:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointTransactionVo implements Serializable {

    // 流水ID
    private Long id;

    // 用户ID
    private Long userId;

    // 用户名
    private String account;

    //变更类型(1:获得 2:消费 3:退款)
    private int changeType;

    // 积分变动值
    private int changePoint;

    // 更变后积分值
    private int pointsAfter;

    //余额变动值
    private  int changeBalance;

    // 更变后余额
    private int balanceAfter;

    // 关联业务ID(如订单号)
    private String bizId;

    // 备注信息
    private String remark;

    // 创建时间（原始时间）
    private LocalDateTime createTime;

}
