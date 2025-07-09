package com.benefit.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Allen
 * @date 2025/6/10 16:21
 * 购物车项实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;


    private Integer itemType;

    private Long itemId;

    private Integer quantity;

    private Boolean selected;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    // 关联的权益对象 可能是权益产品也有可能是权益包
    // BenefitProduct 或 BenefitPackage
    private Object benefitItem;
}