package com.benefit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.benefit.model.entity.BenefitPoints;
import com.benefit.request.BenefitPointsRequest;

/**
 * @author Allen
 * @date 2025/7/4 10:59
 */
public interface BenefitPointsService extends IService<BenefitPoints> {

    int modifyBalance(BenefitPointsRequest request);
}
