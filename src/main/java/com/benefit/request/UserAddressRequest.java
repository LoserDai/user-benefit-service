package com.benefit.request;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author Allen
 * @Date 2025/8/18 9:50
 * @Description
 */

@Data
public class UserAddressRequest extends PageBaseRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private Long id;

    private Long userId;

    private String receiverName;

    private String receiverPhone;

    private String province;

    private String city;

    private Integer status;

    //可以为null
    private String district;

    private String detailAddress;

    private String postalCode;

    private LocalDateTime createTime;

    public boolean checkParamIsExist() {
        return (userId == null || userId.toString().isEmpty()) &&
                (receiverName == null || receiverName.isEmpty()) &&
                (receiverPhone == null || receiverPhone.isEmpty()) &&
                (province == null || province.isEmpty()) &&
                (city == null || city.isEmpty()) &&
                (detailAddress == null || detailAddress.isEmpty()) &&
                (postalCode == null || postalCode.isEmpty());
    }

}
