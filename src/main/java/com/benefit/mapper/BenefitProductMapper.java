package com.benefit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.benefit.model.entity.BenefitProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author Allen
 * @date 2025/6/10 15:14
 */

@Mapper
public interface BenefitProductMapper extends BaseMapper<BenefitProduct> {

    @Select("SELECT COUNT(1) FROM benefit_product WHERE status = 'ACTIVE'")
    Integer getProductCount();
}
