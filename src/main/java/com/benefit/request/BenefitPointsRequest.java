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

    //side = 0 recharge; side = 1 deductions
    private Integer side;

    private Integer points ;

    private BigDecimal balance ;
}
