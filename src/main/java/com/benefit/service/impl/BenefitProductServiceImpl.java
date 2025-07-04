package com.benefit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.benefit.mapper.BenefitProductMapper;
import com.benefit.model.entity.BenefitProduct;
import com.benefit.service.BenefitProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Allen
 * @date 2025/7/4 11:12
 */

@Service
@Slf4j
public class BenefitProductServiceImpl extends ServiceImpl<BenefitProductMapper, BenefitProduct> implements BenefitProductService {
}
