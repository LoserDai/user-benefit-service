package com.benefit.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Allen
 * @date 2025/7/4 16:16
 */
@Data
public class PageResult<T> {
    @ApiModelProperty("数据列表")
    private T data;

    @ApiModelProperty("总记录数")
    private Long total;

    @ApiModelProperty("当前页码")
    private Integer pageNum;

    @ApiModelProperty("每页数量")
    private Integer pageSize;



    public PageResult() {}

    public PageResult(List<T> data, Long total, Integer pageNum, Integer pageSize) {
        this.data = (T) data;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    // 计算信息增强
    public boolean isFirstPage() {
        return pageNum == 1;
    }

}
