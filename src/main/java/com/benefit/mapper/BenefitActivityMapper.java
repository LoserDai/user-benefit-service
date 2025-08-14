package com.benefit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.benefit.model.entity.BenefitActivity;
import com.benefit.model.enums.ActivityStatus;
import com.benefit.request.BenefitActivityRequest;
import com.benefit.vo.BenefitActivityVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Allen
 * @date 2025/6/10 15:13
 */
@Mapper
public interface BenefitActivityMapper extends BaseMapper<BenefitActivity> {
    int updateStatus(@Param("status") ActivityStatus status, @Param("id") Long id);

    List<BenefitActivity> selectExpiredActivities();

    int batchUpdateToEnded(List<BenefitActivity> activities);

    List<BenefitActivity> selectNotStartActivities();

    int batchUpdateToStart(List<BenefitActivity> activities);

    List<BenefitActivityVo> selectPageList(Page<BenefitActivityVo> page, BenefitActivityRequest request);
}
