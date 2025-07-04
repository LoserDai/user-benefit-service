package com.benefit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.benefit.mapper.BenefitPointsMapper;
import com.benefit.model.entity.BenefitPoints;
import com.benefit.service.BenefitPointsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Allen
 * @date 2025/7/4 11:11
 */

@Service
@Slf4j
public class BenefitPointsServiceImpl extends ServiceImpl<BenefitPointsMapper, BenefitPoints> implements BenefitPointsService {
}
