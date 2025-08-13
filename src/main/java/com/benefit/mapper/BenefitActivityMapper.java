package com.benefit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.benefit.model.entity.BenefitActivity;
import com.benefit.model.enums.ActivityStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Allen
 * @date 2025/6/10 15:13
 */
@Mapper
public interface BenefitActivityMapper extends BaseMapper<BenefitActivity> {
    int updateStatus(@Param("status") ActivityStatus status, @Param("id") Long id);
}
