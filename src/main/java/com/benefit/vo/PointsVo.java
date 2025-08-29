package com.benefit.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author Allen
 * @Date 2025/8/29 15:22
 * @Description
 */
@Data
public class PointsVo implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private Long userId;

    private String account;

    private BigDecimal points;

    private BigDecimal balance;


}
