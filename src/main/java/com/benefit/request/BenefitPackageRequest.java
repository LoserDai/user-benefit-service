package com.benefit.request;

import com.benefit.model.enums.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Allen
 * @date 2025/7/22 15:13
 */

@Data
public class BenefitPackageRequest extends PageBaseRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

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

    @ApiModelProperty("页码")
    private Integer pageNum;

    @ApiModelProperty("每页数量")
    private Integer pageSize;

    @ApiModelProperty("排序字段")
    private String sortField;

    @ApiModelProperty("排序方式: asc/desc")
    private String sortOrder;
}
