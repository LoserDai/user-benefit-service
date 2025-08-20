package com.benefit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.benefit.common.PageResult;
import com.benefit.exception.BusinessException;
import com.benefit.mapper.*;
import com.benefit.model.entity.*;
import com.benefit.model.enums.ErrorCode;
import com.benefit.model.enums.OrderStatus;
import com.benefit.request.BenefitActivityRequest;
import com.benefit.request.BenefitPointsRequest;
import com.benefit.request.OrderMainRequest;
import com.benefit.service.BenefitPointsService;
import com.benefit.service.OrderMainService;
import com.benefit.vo.BenefitActivityVo;
import com.benefit.vo.OrderMainVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @Author Allen
 * @Date 2025/8/20 10:29
 * @Description
 */
@Service
@Slf4j
@Transactional
public class OrderMainServiceImpl extends ServiceImpl<OrderMainMapper, OrderMain> implements OrderMainService {


    @Resource
    private OrderMainMapper orderMainMapper;

    @Resource
    private ShoppingCartMapper shoppingCartMapper;

    @Resource
    private BenefitPackageMapper benefitPackageMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private BenefitPointsMapper benefitPointsMapper;

    @Resource
    private BenefitPointsService benefitPointsService;

    @Override
    public int createOrderMain(long userId) {

        //获取生效的购物车
        ShoppingCart shoppingCart = shoppingCartMapper.selectByUserId(userId);
        if (ObjectUtils.isNull(shoppingCart)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "this user hasn't shoppingCart data!");
        }

        //转换成订单
        List<CartItem> list = shoppingCart.getCartItems();
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "This shoppingCart hasn't data!");
        }

        OrderMain orderMain = new OrderMain();
        orderMain.setUserId(userId);
        orderMain.setTotalPoint(shoppingCart.getTotalSelectedPoints());
        orderMain.setStatus(OrderStatus.PENDING_PAYMENT);
        orderMain.setCreateTime(LocalDateTime.now());
        orderMain.setUpdateTime(LocalDateTime.now());

        int countOrderMain = orderMainMapper.insert(orderMain);
        if (countOrderMain <= 0) {
            log.error("insert mainOrder fail!");
            return 0;
        }
        //关闭原来的shoppingCart
        shoppingCart.setStatus(0);
        int i = shoppingCartMapper.updateById(shoppingCart);
        if (i <= 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"close shoppingCart failed!");
        }

        Set<Integer> set = new HashSet<>(Arrays.asList(1, 2, 3));

        int countOrderItem = 0;
        //校验库存是否足够
        for (CartItem item : list) {

            Integer itemType = item.getItemType();
            Integer quantity = item.getQuantity();
            Long itemId = item.getItemId();
            String itemName = item.getItemName();

            if (!set.contains(itemType)) {
                log.info("item type is {}", itemType);
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "Item type not exist!");
            }

            if (itemId.equals(2L)) {
                BenefitPackage benefitPackage = benefitPackageMapper.selectById(itemId);
                Integer count = benefitPackage.getQuantity();
                if (count <= 0 || item.getQuantity() > count) {
                    log.info("剩余库存： {},购物车需要：{}", count, item.getQuantity());
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR, "Quantity isn't enough!");
                }
            }

            OrderItem orderItem = new OrderItem();

            orderItem.setOrderId(orderMain.getId());
            orderItem.setItemType(itemType);
            orderItem.setItemName(itemName);
            orderItem.setItemId(itemId);
            orderItem.setQuantity(quantity);
            orderItem.setPointPrice(item.getPointPrice());
            orderItem.setTotalPoint(item.getPointPrice().multiply(BigDecimal.valueOf(item.getQuantity())));

            countOrderItem = orderItemMapper.insert(orderItem);
            if (countOrderItem <= 0) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "save orderItem failed!");
            }
            countOrderItem++;
        }

        return countOrderItem;
    }

    @Override
    public int cancelOrderMain(long userId,String cancelReason) {

        //获取该用户的订单
        OrderMain orderMain = orderMainMapper.selectByUserId(userId);
        //修改状态
        int countCancel = orderMainMapper.cancelOrder(cancelReason,orderMain.getId());
        if (countCancel <= 0 ){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"cancel failed");
        }
        return countCancel;
    }

    @Override
    public int payOrderMain(long userId) {

        //获取未支付的订单
        OrderMain orderMain = orderMainMapper.selectByUserId(userId);
        if (ObjectUtils.isNull(orderMain)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "The user has no unpaid orders");
        }
        //校验积分是否足够
        BenefitPoints userPoints = benefitPointsMapper.selectOne(new QueryWrapper<BenefitPoints>().eq("user_id", userId));
        if (new BigDecimal(userPoints.getPoints()).compareTo(orderMain.getTotalPoint()) < 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "points is not enough to pay! please recharge!");
        }
        //如果够，扣除积分
        BenefitPointsRequest req = new BenefitPointsRequest();
        req.setUserId(userId);
        req.setSide(1);
        req.setPoints(orderMain.getTotalPoint().intValue());
        req.setBalance(0);
        //记账落库
        int modified = benefitPointsService.modifyBalance(req);
        if (modified <= 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "save pointTransaction failed;");
        }
        //支付完成，更新订单状态
        int payCount = orderMainMapper.updatePayTime(orderMain.getId());
        if (payCount <= 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "update orderStatus to payOver failed;");
        }
        return payCount;
    }

    @Override
    public PageResult<OrderMainVo> queryOrderMain(OrderMainRequest request) {

        int pageNum = Optional.ofNullable(request)
                .map(OrderMainRequest::getPageNum)
                .filter(num -> num > 0)
                .orElse(1);

        int pageSize = Optional.ofNullable(request)
                .map(OrderMainRequest::getPageSize)
                .filter(size -> size > 0 && size <= 500)
                .orElse(10);

        Page<OrderMainVo> page = new Page<>(pageNum, pageSize);
        List<OrderMainVo> list = orderMainMapper.pageQuery(page,request);

        return new PageResult<>(
                list,
                page.getTotal(),
                (int) page.getCurrent(),
                (int) page.getSize());
    }
}
