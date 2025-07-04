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
    private List<T> list;

    @ApiModelProperty("总记录数")
    private Long total;

    @ApiModelProperty("当前页码")
    private Integer pageNum;

    @ApiModelProperty("每页数量")
    private Integer pageSize;

    @ApiModelProperty("总页数")
    private Integer pages;

    public PageResult() {}

    public PageResult(List<T> list, Long total, Integer pageNum, Integer pageSize, Integer pages) {
        this.list = list;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = pages;
    }
}
