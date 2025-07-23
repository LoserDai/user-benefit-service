package com.benefit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.benefit.model.entity.BenefitPackage;
import com.benefit.request.BenefitPackageRequest;
import com.benefit.vo.BenefitPackageVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Allen
 * @date 2025/6/10 15:12
 */
@Mapper
public interface BenefitPackageMapper extends BaseMapper<BenefitPackage> {

    List<BenefitPackageVo> selectPackageList(Page<BenefitPackageVo> page, BenefitPackageRequest request);
}
