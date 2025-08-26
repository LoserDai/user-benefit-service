package com.benefit.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author Allen
 * @Date 2025/8/26 10:50
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDashVo implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private LocalDateTime registerDate;
    private Integer userCount;
}
