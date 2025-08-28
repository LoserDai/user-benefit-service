package com.benefit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.benefit.model.entity.OrderMain;
import com.benefit.request.OrderMainRequest;
import com.benefit.vo.OrderDashVo;
import com.benefit.vo.OrderMainVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author Allen
 * @date 2025/6/10 16:52
 */
@Mapper
public interface OrderMainMapper extends BaseMapper<OrderMain> {
    OrderMain selectByUserId(@Param("userId") long userId,@Param("orderNo") String orderNo);

    int cancelOrder(@Param("cancelReason") String cancelReason, @Param("id") Long id);

    int updatePayTime(@Param("id")Long id);

    List<OrderMainVo> pageQuery(Page<OrderMainVo> page, OrderMainRequest request);

    List<OrderDashVo> getOrderCount();

    List<OrderMain> checkAndCloseExpireOrder();

    Integer batchCancelOrder(List<OrderMain> list);

    @Select("SELECT COUNT(1) as orderMainCount, SUM(total_point) as totalPoints FROM t_order_main")
    Map<String, Object> getOrderMainCount();
}
