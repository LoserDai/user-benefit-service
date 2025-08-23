package com.benefit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.benefit.model.entity.CartItem;
import com.benefit.request.CartItemRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Allen
 * @date 2025/6/10 16:50
 */
@Mapper
public interface CartItemMapper extends BaseMapper<CartItem> {
    Integer deletedShoppingCart(@Param("userId") long userId, @Param("id") Integer id);
}
