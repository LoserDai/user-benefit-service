package com.benefit.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Allen
 * @Date 2025/8/23 17:28
 * @Description
 */
@Data
public class CancelOrderRequest extends PageBaseRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String cancelReason;
    private String orderNo;
    private Long userId;


}
