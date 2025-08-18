package com.benefit.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author Allen
 * @Date 2025/8/18 9:40
 * @Description 用户收货地址
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user_address")
public class UserAddress implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String receiverName;

    private String receiverPhone;

    //状态(0:不可用； 1：可用)
    private Integer status;

    private String province;

    private String city;

    private String district;

    private String detailAddress;

    private String postalCode;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
