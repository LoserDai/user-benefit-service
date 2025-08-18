package com.benefit.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.benefit.model.entity.CartItem;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author Allen
 * @Date 2025/8/18 15:03
 * @Description
 */

@Data
public class ShoppingCartRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    @TableId(type = IdType.AUTO)
    private Long id;

    // 关联用户ID（唯一）
    private Long userId;

    //状态: 0-失效; 1-活跃
    private Integer status;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;

    // 购物车项列表（非数据库字段）
    private List<CartItem> cartItems;

    // 选中商品总积分
    private BigDecimal totalSelectedPoints;

}
