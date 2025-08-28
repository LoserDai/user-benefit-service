package com.benefit.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author Allen
 * @Date 2025/8/28 15:46
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private Long id;

    private String account;

    private Integer gender;

    private String email;

    private String phone;

    private String status;

    private String userRole;

    private LocalDateTime createTime;

}
