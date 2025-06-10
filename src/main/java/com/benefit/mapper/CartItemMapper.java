package com.benefit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.benefit.model.entity.CartItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Allen
 * @date 2025/6/10 16:50
 */
@Mapper
public interface CartItemMapper extends BaseMapper<CartItem> {
}
