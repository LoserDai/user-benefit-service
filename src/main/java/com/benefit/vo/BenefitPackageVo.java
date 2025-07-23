package com.benefit.vo;

import com.benefit.model.enums.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Allen
 * @date 2025/7/22 15:25
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BenefitPackageVo implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;
    @ApiModelProperty("权益包ID")
    private Long id;

    @ApiModelProperty("权益包")
    private String packageName;

    @ApiModelProperty("权益产品")
    private String productName;

    @ApiModelProperty("状态")
    private Status status;

    @ApiModelProperty("库存")
    private Integer quantity;

    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
}
