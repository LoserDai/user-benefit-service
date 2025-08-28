package com.benefit.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author Allen
 * @Date 2025/8/28 13:58
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashVo implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private Integer userCount;

    private Integer productCount;

    private Object orderMainCount;

    private Object orderPointsCount;

}
