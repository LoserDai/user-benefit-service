package com.benefit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.benefit.common.PageResult;
import com.benefit.model.entity.BenefitPoints;
import com.benefit.request.BenefitPointsRequest;
import com.benefit.request.PointsRequest;
import com.benefit.vo.PointsVo;

/**
 * @author Allen
 * @date 2025/7/4 10:59
 */
public interface BenefitPointsService extends IService<BenefitPoints> {

    int modifyBalance(BenefitPointsRequest request);

    PageResult<PointsVo> pageQuery(PointsRequest request);
}
