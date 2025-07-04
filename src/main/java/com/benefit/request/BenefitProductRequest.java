package com.benefit.request;

import com.benefit.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Allen
 * @date 2025/7/4 14:53
 */

@Data
public class BenefitProductRequest extends PageBaseRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;


    private BigDecimal price;

    private String remark;



    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("产品状态: 0-未激活, 1-激活")
    private Status status;

    @ApiModelProperty("最低价格")
    private BigDecimal minPrice;

    @ApiModelProperty("最高价格")
    private BigDecimal maxPrice;

    @ApiModelProperty("创建时间起始")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeStart;

    @ApiModelProperty("创建时间结束")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeEnd;

    @ApiModelProperty("页码")
    private Integer pageNum;

    @ApiModelProperty("每页数量")
    private Integer pageSize;

    @ApiModelProperty("排序字段")
    private String sortField;

    @ApiModelProperty("排序方式: asc/desc")
    private String sortOrder;

}
