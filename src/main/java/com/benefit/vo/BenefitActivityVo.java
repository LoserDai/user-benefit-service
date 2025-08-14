package com.benefit.vo;

import com.benefit.model.enums.ActivityStatus;
import com.benefit.model.enums.ActivityType;
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
 * @date 2025/8/13 17:38
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BenefitActivityVo implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    @ApiModelProperty("权益活动ID")
    private Long id;

    @ApiModelProperty("权益活动名称")
    private String activityName;

    @ApiModelProperty("权益包名称")
    private String packageName;

    @ApiModelProperty("活动开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("活动结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty("状态")
    private ActivityStatus status;

    @ApiModelProperty("活动类型")
    private ActivityType activityType;

    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("最低购买限制")
    private Integer minPurchase;

    @ApiModelProperty("限购数量")
    private Integer purchaseLimit;

    @ApiModelProperty("折扣值")
    private BigDecimal discountValue;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
}
