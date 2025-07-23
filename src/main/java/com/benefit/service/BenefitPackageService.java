package com.benefit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.benefit.common.PageResult;
import com.benefit.model.entity.BenefitPackage;
import com.benefit.request.BenefitPackageRequest;
import com.benefit.vo.BenefitPackageVo;


/**
 * @author Allen
 * @date 2025/7/4 10:52
 */
public interface BenefitPackageService  extends IService<BenefitPackage> {
    PageResult<BenefitPackageVo> queryPackage(BenefitPackageRequest request);
}
