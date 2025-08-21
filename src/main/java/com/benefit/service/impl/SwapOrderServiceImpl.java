package com.benefit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.benefit.exception.BusinessException;
import com.benefit.mapper.SwapOrderMapper;
import com.benefit.model.entity.SwapConfig;
import com.benefit.model.entity.SwapOrder;
import com.benefit.model.enums.ErrorCode;
import com.benefit.request.BenefitPointsRequest;
import com.benefit.service.BenefitPointsService;
import com.benefit.service.SwapConfigService;
import com.benefit.service.SwapOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Allen
 * @date 2025/7/11 16:40
 */
@Service
public class SwapOrderServiceImpl extends ServiceImpl<SwapOrderMapper, SwapOrder> implements SwapOrderService {

    @Resource
    private SwapConfigService swapConfigService;

    @Resource
    private BenefitPointsService benefitPointsService;

    @Resource
    private SwapOrderMapper swapOrderMapper;

    @Override
    @Transactional
    public int saveSwapOrder(SwapOrder order, long userId) {

        SwapConfig config = swapConfigService.getOne(new QueryWrapper<SwapConfig>().eq("user_id", userId)
                .eq("ccy",order.getCcy()));

        long currentTimeMillis = System.currentTimeMillis();
        String orderId = String.valueOf(currentTimeMillis);
        order.setOrderId(orderId);
        order.setCustomerId(String.valueOf(userId));

        order.setExchangeFee(config.getFee());
        order.setExchangeRate(config.getRate());
        order.setCreatedBy("SYSTEM");
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedBy("SYSTEM");
        order.setUpdatedAt(LocalDateTime.now());
        order.setOperator("SYSTEM");

        order.setStatus("SUCCESS");


        //记账
        BenefitPointsRequest req = new BenefitPointsRequest();
        req.setUserId(userId);

        int reduceCount = 0;
        int addCount = 0;
        if("B/P".equals(order.getCcy())){
            order.setType("Balance swap to Points");
            //扣减
            req.setSide(1);
            req.setBalance(order.getAmountSell());
            req.setPoints(BigDecimal.ZERO);
            reduceCount = benefitPointsService.modifyBalance(req);
            //增值
            req.setSide(0);
            req.setBalance(BigDecimal.ZERO);
            req.setPoints(order.getAmountBuy());
            addCount = benefitPointsService.modifyBalance(req);
        } else if ("P/B".equals(order.getCcy())) {
            order.setType("Points swap to Balance");
            //扣减
            req.setSide(1);
            req.setPoints(order.getAmountSell());
            req.setBalance(BigDecimal.ZERO);
            reduceCount = benefitPointsService.modifyBalance(req);
            //增值
            req.setSide(0);
            req.setBalance(order.getAmountBuy());
            req.setPoints(BigDecimal.ZERO);
            addCount = benefitPointsService.modifyBalance(req);
        }else {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"ccy not in (B/P,P/B)");
        }
        int insert = swapOrderMapper.insert(order);
        return insert;
    }
}
