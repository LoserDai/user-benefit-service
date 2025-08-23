package com.benefit.vo;

import com.benefit.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author Allen
 * @Date 2025/8/20 17:13
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderMainVo implements Serializable {
    private static final long serialVersionUID = 3191241716373120793L;

    private Long userId;

    private Integer status;

    private Long orderId;

    //商品类型(1:权益产品 2:权益包 3:其他类型)
    private Integer itemType;

    //商品名称
    private String itemName;

    private Integer quantity;

    //单价
    private BigDecimal pointPrice;

    //总价
    private BigDecimal totalPoint;
}
