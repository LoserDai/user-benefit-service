package com.benefit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.benefit.exception.BusinessException;
import com.benefit.mapper.CartItemMapper;
import com.benefit.mapper.ShoppingCartMapper;
import com.benefit.mapper.UserMapper;
import com.benefit.model.entity.CartItem;
import com.benefit.model.entity.ShoppingCart;
import com.benefit.model.entity.User;
import com.benefit.model.enums.ErrorCode;
import com.benefit.request.ShoppingCartRequest;
import com.benefit.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Allen
 * @Date 2025/8/18 15:16
 * @Description
 */
@Service
@Slf4j
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {


    @Resource
    private ShoppingCartMapper shoppingCartMapper;

    @Resource
    private CartItemMapper cartItemMapper;

    @Resource
    private UserMapper userMapper;

    /**
    * @Description: create shoppingCart
    * @Param: [request]
    * @Return: java.lang.Integer
    * @Author: Allen
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createShoppingCart(ShoppingCartRequest request) {
        // 1. 获取或创建购物车
        ShoppingCart cart = getOrCreateCart(request);

        // 2. 批量保存购物车项
        return batchSaveCartItems(request.getCartItems(), cart);
    }


    /**
    * @Description: show ShoppingCart
    * @Param: [userId]
    * @Return: com.benefit.model.entity.ShoppingCart
    * @Author: Allen
    */
    @Override
    public ShoppingCart showShoppingCart(long userId) {
        User user = userMapper.selectById(userId);
        if (ObjectUtils.isNull(user)){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"This user isn't exist!");
        }

        return shoppingCartMapper.selectByUserId(userId);
    }

    @Override
    public Integer updateShoppingCart(ShoppingCartRequest request) {

        List<CartItem> list = request.getCartItems();
        //先将所有的删除，再插入？
        int deleteCount = cartItemMapper.delete(new QueryWrapper<CartItem>().eq("user_id", request.getUserId())
                .eq("cart_id", request.getId()));
        log.info("deleted cartItem count: {}", deleteCount);
        //插入数据
        if (list.isEmpty()){
            log.info("user clear the shoppingCart!");
            return 0;
        }
        int insertCount = 0;
        for (CartItem item : list) {
            insertCount = cartItemMapper.insert(item);
            log.info("insert cartItem: {}",item.getItemName());
            insertCount ++;
        }

        return insertCount;
    }

    /**
    * @Description: creat shoppingCart
    * @Param: [request]
    * @Return: com.benefit.model.entity.ShoppingCart
    * @Author: Allen
    */
    private ShoppingCart getOrCreateCart(ShoppingCartRequest request) {
        ShoppingCart cart = shoppingCartMapper.selectByUserId(request.getUserId());

        // 购物车已存在直接返回
        if (cart != null) {
            //修改totalSelectedPoints
            cart.setTotalSelectedPoints(cart.getTotalSelectedPoints().add(request.getTotalSelectedPoints()));
            int count = shoppingCartMapper.updateById(cart);
            if (count <= 0){
                throw new BusinessException(ErrorCode.SYSTEM_ERROR,"update shoppingCart's totalPoints failed!");
            }
            log.info("update shoppingCart's totalPoints,after: {}",cart.getTotalSelectedPoints());
            return cart;
        }

        // 创建新购物车
        ShoppingCart newCart = new ShoppingCart();
        newCart.setUserId(request.getUserId());
        newCart.setStatus(1);
        newCart.setTotalSelectedPoints(request.getTotalSelectedPoints());

        // 设置创建/更新时间
        LocalDateTime now = LocalDateTime.now();
        newCart.setCreateTime(now);
        newCart.setUpdateTime(now);

        if (shoppingCartMapper.insert(newCart) <= 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "create shoppingCart failed!");
        }

        return newCart;
    }

    /**
    * @Description: batchSaveCartItems
    * @Param: [items, cart]
    * @Return: java.lang.Integer
    * @Author: Allen
    */
    private Integer batchSaveCartItems(List<CartItem> items, ShoppingCart cart) {
        if (CollectionUtils.isEmpty(items)) {
            log.info("shopping is null，user_id is: {}", cart.getUserId());
            return 0;
        }

        // 1. 准备批量插入数据
        List<CartItem> batchItems = items.stream()
                .map(item -> convertToEntity(item, cart))
                .collect(Collectors.toList());

        // 2. 批量插入
        for (CartItem batchItem : batchItems) {
            int count = cartItemMapper.insert(batchItem);
            if (count <= 0) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "save shopping failed!");
            }
        }

        // 3. 记录日志（避免循环内记录）
        logItemsInfo(batchItems);

        return batchItems.size();
    }

    /**
    * @Description: 数据转换
    * @Param: [item, cart]
    * @Return: com.benefit.model.entity.CartItem
    * @Author: Allen
    */
    private CartItem convertToEntity(CartItem item, ShoppingCart cart) {
        CartItem entity = new CartItem();

        entity.setCartId(cart.getId());
        entity.setUserId(cart.getUserId());
        entity.setItemType(item.getItemType());
        entity.setItemId(item.getItemId());
        entity.setItemName(item.getItemName());
        entity.setItemImage(item.getItemImage());
        entity.setPointPrice(item.getPointPrice());
        entity.setQuantity(item.getQuantity());

        // 设置时间（建议使用自动填充）
        LocalDateTime now = LocalDateTime.now();
        entity.setCreateTime(now);
        entity.setUpdateTime(now);

        return entity;
    }

    /**
    * @Description: 记录日志
    * @Param: [items]
    * @Return: void
    * @Author: Allen
    */
    private void logItemsInfo(List<CartItem> items) {
        if (log.isInfoEnabled()) {
            String itemNames = items.stream()
                    .map(CartItem::getItemName)
                    .collect(Collectors.joining(", "));
            log.info("User {} add {} goods: [{}]",
                    items.get(0).getUserId(),
                    items.size(),
                    itemNames);
        }
    }
}
