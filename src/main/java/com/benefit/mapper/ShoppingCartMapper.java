package com.benefit.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.benefit.model.entity.ShoppingCart;
import com.benefit.vo.ShoppingCartVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * @author Allen
 * @date 2025/6/10 16:53
 */
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {


    List<Map<String, Object>> selectVoByUserId(@Param("userId") Long userId);

    @Select({
            "<script>",
            "SELECT id, user_id, total_selected_points, create_time, update_time",
            "FROM t_shopping_cart",
            "WHERE user_id = #{userId}",
            "AND status = 1",
            "LIMIT 1",
            "</script>"
    })
    ShoppingCart selectByUserId(@Param("userId") Long userId);


    Integer clearShoppingCart(@Param("userId") long userId);
}
