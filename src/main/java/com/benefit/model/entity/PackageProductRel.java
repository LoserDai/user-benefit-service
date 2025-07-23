package com.benefit.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Allen
 * @date 2025/7/23 17:11
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackageProductRel implements Serializable {

    private Long packageId;

    private Long productId;

    private LocalDateTime createTime;
}
