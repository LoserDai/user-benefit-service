package com.benefit.model.entity;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Allen
 * @date 2025/6/10 16:26
 * 积分流水
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointTransaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    // 变更类型(1:加值 2:消费 3:退款 4:冻结)
    @Column(name = "change_type", nullable = false)
    private Integer changeType;

    //变更积分
    @Column(name = "change_point", nullable = false)
    private Integer changePoint;

    //更变后的积分
    @Column(name = "pointsAfter", nullable = false)
    private Integer pointsAfter;

    @Column(name = "change_balance", nullable = false)
    private Integer changeBalance;

    //更变后的积分
    @Column(name = "balance_after", nullable = false)
    private Integer balanceAfter;

    // 关联业务ID
    @Column(name = "biz_id")
    private String bizId;

    @Column(name = "remark")
    private String remark;

    @Column(name = "create_time")
    private LocalDateTime createTime;
}
