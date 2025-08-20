package com.benefit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.benefit.common.PageResult;
import com.benefit.model.entity.OrderMain;
import com.benefit.request.OrderMainRequest;
import com.benefit.vo.OrderMainVo;

/**
 * @Author Allen
 * @Date 2025/8/15 17:25
 * @Description
 */
public interface OrderMainService extends IService<OrderMain> {
    int createOrderMain(long userId);

    int cancelOrderMain(long userId,String cancelReason);

    int payOrderMain(long userId);

    PageResult<OrderMainVo> queryOrderMain(OrderMainRequest request);
}
