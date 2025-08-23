package com.benefit.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author Allen
 * @Date 2025/8/18 10:56
 * @Description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressVo implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private Long id;

    private Long userId;

    private String receiverName;

    private String receiverPhone;

    private String province;

    private String city;

    private int status;

    private String district;

    private String detailAddress;

    private String postalCode;


    private LocalDateTime createTime;
}
