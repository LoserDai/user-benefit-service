package com.benefit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.benefit.model.entity.BenefitPoints;
import com.benefit.request.PointsRequest;
import com.benefit.vo.PointsVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Allen
 * @date 2025/6/10 15:10
 */
@Mapper
public interface BenefitPointsMapper extends BaseMapper<BenefitPoints> {
    List<PointsVo> pageQuery(Page<PointsVo> page, PointsRequest request);
}
