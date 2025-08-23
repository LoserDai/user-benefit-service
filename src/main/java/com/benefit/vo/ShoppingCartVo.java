package com.benefit.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author Allen
 * @Date 2025/8/23 11:26
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartVo implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private Long id;

    private Long userId;

    private BigDecimal totalSelectedPoints;

    private Integer quantity;

    private String itemName;

    private String itemImage;

    private Integer itemType;

    private BigDecimal pointPrice;

    private LocalDateTime createTime;
}
