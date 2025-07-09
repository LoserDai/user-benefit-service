package com.benefit.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.benefit.model.enums.Status;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * @author Allen
 * @date 2025/6/6 14:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String account;

    private Integer gender;

    private String password;

    private String email;

    private String phone;

    private Status status;

    //是否为管理员 0 - 普通用户 1 - 管理员
    private Integer userRole;

    private String createdBy;

    private String updatedBy;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
