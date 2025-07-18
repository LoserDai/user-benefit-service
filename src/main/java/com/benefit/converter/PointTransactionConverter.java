package com.benefit.converter;

import com.benefit.model.entity.PointTransaction;
import com.benefit.vo.PointTransactionVo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Allen
 * 积分交易流水转换工具
 * @date 2025/7/18 17:48
 */

@Component
public class PointTransactionConverter {

    /**
     * 将实体转换为VO对象
     * @param entity  数据库实体
     * @param account 用户名（从其他服务获取）
     * @return VO对象
     */
    public PointTransactionVo toVO(PointTransaction entity, String account) {
        PointTransactionVo vo = new PointTransactionVo();

        vo.setId(entity.getId());
        vo.setUserId(entity.getUserId());
        vo.setAccount(account);
        vo.setChangePoint(entity.getChangePoint());
        vo.setPointsAfter(entity.getPointsAfter());
        vo.setChangeType(entity.getChangeType());
        vo.setBizId(entity.getBizId());

        vo.setChangeBalance(entity.getChangeBalance());
        vo.setBalanceAfter(entity.getBalanceAfter());
        vo.setCreateTime(entity.getCreateTime());
        vo.setRemark(entity.getRemark());
        return vo;
    }

    /**
     * 批量转换
     * @param entities         实体列表
     * @param usernameProvider 用户名提供函数
     * @return VO列表
     */
    public List<PointTransactionVo> toVOList(
            List<PointTransaction> entities,
            Function<Long, String> usernameProvider
    ) {
        return entities.stream()
                .map(entity -> {
                    String username = usernameProvider.apply(entity.getUserId());
                    return toVO(entity, username);
                })
                .collect(Collectors.toList());
    }
}
