package com.benefit.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Allen
 * @date 2025/7/28 16:48
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityPackageRel {

    private Long activityId;

    private Long packageId;

    private LocalDateTime createTime;
}
