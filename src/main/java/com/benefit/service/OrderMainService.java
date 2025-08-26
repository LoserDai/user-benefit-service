package com.benefit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.benefit.common.PageResult;
import com.benefit.model.entity.OrderMain;
import com.benefit.request.CancelOrderRequest;
import com.benefit.request.OrderMainRequest;
import com.benefit.vo.OrderDashVo;
import com.benefit.vo.OrderMainVo;

import java.util.List;

/**
 * @Author Allen
 * @Date 2025/8/15 17:25
 * @Description
 */
public interface OrderMainService extends IService<OrderMain> {
    int createOrderMain(long userId,long addressId);

    int cancelOrderMain(CancelOrderRequest request);

    int payOrderMain(long userId,String orderNo);

    PageResult<OrderMainVo> queryOrderMain(OrderMainRequest request);

    List<OrderDashVo> getOrderCount();
}
