package com.benefit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.benefit.model.entity.ActivityPackageRel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Allen
 * @date 2025/7/28 16:49
 */
@Mapper
public interface ActivityPackageRelMapper extends BaseMapper<ActivityPackageRel> {

    @Insert("<script>" +
            "INSERT INTO activity_package_rel (activity_id, package_id,create_time) VALUES " +
            "<foreach collection='packageIdList' item='packageId' separator=','>" +
            "(#{activityId}, #{packageId}, NOW())" +
            "</foreach>" +
            "</script>")
    int insertPackageProductRelBatch(@Param("activityId") Long activityId, @Param("packageIdList") List<Long> packageIdList);
}
