package com.benefit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.benefit.model.entity.PackageProductRel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Allen
 * @date 2025/7/23 17:13
 */
@Mapper
public interface PackageProductRelMapper extends BaseMapper<PackageProductRel> {
    @Insert("<script>" +
            "INSERT INTO package_product_rel (package_id, product_id,create_time) VALUES " +
            "<foreach collection='productIds' item='productId' separator=','>" +
            "(#{packageId}, #{productId}, NOW())" +
            "</foreach>" +
            "</script>")
    int insertPackageProductRelBatch(@Param("packageId") Long packageId, @Param("productIds") List<Long> productIds);
}



