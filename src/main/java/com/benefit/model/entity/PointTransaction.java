package com.benefit.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;


import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Allen
 * @date 2025/6/10 16:26
 * 积分流水
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointTransaction implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    // 变更类型(1:加值 2:消费 3:退款 4:冻结)
    private Integer changeType;

    //变更积分
    private Integer changePoint;

    //更变后的积分
    private Integer pointsAfter;

    private Integer changeBalance;

    //更变后的积分
    private Integer balanceAfter;

    // 关联业务ID
    private String bizId;

    private String remark;

    private LocalDateTime createTime;
}
