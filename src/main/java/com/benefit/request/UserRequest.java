package com.benefit.request;

import com.benefit.model.enums.Status;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author Allen
 * @Date 2025/8/28 15:32
 * @Description
 */
@Data
public class UserRequest extends PageBaseRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String account;

    private Integer gender;

    private String email;

    private String phone;

    private String status;

    //是否为管理员 0 - 普通用户 1 - 管理员
    private Integer userRole;

    private LocalDateTime createTime;
}
