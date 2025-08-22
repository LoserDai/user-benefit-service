package com.benefit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.benefit.model.entity.PointTransaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author Allen
 * @date 2025/6/10 16:52
 */
@Mapper
public interface PointTransactionMapper extends BaseMapper<PointTransaction> {
    Map<String,BigDecimal> queryConsumed(@Param("userId") long userId, @Param("changeType") String changeType);
}
