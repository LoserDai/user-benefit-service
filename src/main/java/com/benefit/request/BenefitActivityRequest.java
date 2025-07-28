package com.benefit.request;

import com.benefit.model.enums.ActivityStatus;
import com.benefit.model.enums.ActivityType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Allen
 * @date 2025/7/28 14:47
 */
@Data
public class BenefitActivityRequest extends PageBaseRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    @ApiModelProperty("活动ID")
    private Long id;

    // 活动名称
    @ApiModelProperty("活动名称")
    private String activityName;

    // 活动描述
    @ApiModelProperty("活动描述")
    private String description;

    // 活动类型
    @ApiModelProperty("活动类型")
    private ActivityType activityType;

    // 活动状态
    @ApiModelProperty("活动状态")
    private ActivityStatus status;

    // 活动开始时间
    @ApiModelProperty("活动开始时间")
    private LocalDateTime startTime;

    // 活动结束时间
    @ApiModelProperty("活动结束时间")
    private LocalDateTime endTime;

    // 折扣值（0.8表示8折，20表示减20元）
    @ApiModelProperty("折扣值")
    private BigDecimal discountValue;

    // 精度10位，小数2位（最大99999999.99）
    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty("备注")
    private String remark;

    // 最低购买限制（可选）
    @ApiModelProperty("最低购买限制")
    private Integer minPurchase;

    // 限购数量（可选）
    @ApiModelProperty("限购数量")
    private Integer purchaseLimit;

    @ApiModelProperty("关联的权益包ID")
    private List<String> packageIds;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

    /**
     * 检查入参是否都存在
     * @return boolean
     */
    public boolean checkParamIsExist() {
        return (activityName == null || activityName.isEmpty()) &&
                activityType == null &&
                status == null &&
                startTime == null &&
                endTime == null &&
                discountValue == null &&
                price == null &&
                minPurchase == null &&
                purchaseLimit == null;
    }
}
