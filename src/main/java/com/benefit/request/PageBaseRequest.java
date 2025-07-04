package com.benefit.request;

import lombok.Data;

/**
 * @author Allen
 * @date 2025/7/4 16:18
 */
@Data
public class PageBaseRequest {

    // 分页参数
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
