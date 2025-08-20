package com.benefit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.benefit.model.entity.OrderMain;
import com.benefit.request.OrderMainRequest;
import com.benefit.vo.OrderMainVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Allen
 * @date 2025/6/10 16:52
 */
@Mapper
public interface OrderMainMapper extends BaseMapper<OrderMain> {
    OrderMain selectByUserId(long userId);

    int cancelOrder(@Param("cancelReason") String cancelReason, @Param("id") Long id);

    int updatePayTime(@Param("id")Long id);

    List<OrderMainVo> pageQuery(Page<OrderMainVo> page, OrderMainRequest request);
}
