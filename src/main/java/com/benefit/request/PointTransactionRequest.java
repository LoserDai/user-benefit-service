package com.benefit.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Allen
 * @date 2025/7/21 17:11
 */
@Data
public class PointTransactionRequest extends PageBaseRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("用户名称")
    private String account;

    @ApiModelProperty("1:获得 2:消费 3:退款 4:冻结")
    private Integer changeType;

    @ApiModelProperty("业务ID(订单号)")
    private String bizId;

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
