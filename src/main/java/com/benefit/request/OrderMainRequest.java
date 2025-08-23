package com.benefit.request;

import com.benefit.model.enums.OrderStatus;
import lombok.Data;

import java.io.Serializable;


/**
 * @Author Allen
 * @Date 2025/8/20 17:06
 * @Description
 */
@Data
public class OrderMainRequest extends PageBaseRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private Long userId;

    private String orderNo;

    private Integer status;

    private Long orderId;

    //商品类型(1:权益产品 2:权益包 3:其他类型)
    private Integer itemType;

    //商品名称
    private String itemName;

}
