package com.benefit.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Allen
 * @date 2025/7/11 15:15
 */

@Data
public class BenefitPointsRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private Long userId;

    // 1： 兑换消费  2：调账  3：购买消费
    private Integer type;

    //side = 0 充值; side = 1 扣减
    private Integer side;

    private BigDecimal points ;

    private BigDecimal balance ;
}
