package com.benefit.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.benefit.model.enums.Status;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Allen
 * @date 2025/6/6 14:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BenefitProduct implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String productName;

    private BigDecimal price;

    private String remark;

    private Status status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
