package com.benefit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.benefit.exception.BusinessException;
import com.benefit.mapper.BenefitPointsMapper;
import com.benefit.model.entity.BenefitPoints;
import com.benefit.model.enums.ErrorCode;
import com.benefit.request.BenefitPointsRequest;
import com.benefit.service.BenefitPointsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author Allen
 * @date 2025/7/4 11:11
 */

@Service
@Slf4j
public class BenefitPointsServiceImpl extends ServiceImpl<BenefitPointsMapper, BenefitPoints> implements BenefitPointsService {

    @Resource
    private BenefitPointsMapper benefitPointsMapper;




    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyBalance(BenefitPointsRequest request) {
        // 参数校验增强
        if (request == null || request.getUserId() == null) {
            throw new IllegalArgumentException("请求参数不能为空");
        }

        Long userId = request.getUserId();
        Integer side = request.getSide();
        Integer pointsChange = request.getPoints();
        BigDecimal balanceChange = request.getBalance();

        // 校验操作类型
        if (side == null || (side != 0 && side != 1)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "无效的操作类型");
        }

        // 校验金额有效性
        if (pointsChange == null || pointsChange < 0 ||
                balanceChange == null || balanceChange.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "无效的金额参数");
        }

        // 查询现有账户（添加锁机制防止并发问题）
        BenefitPoints account = benefitPointsMapper.selectOne(
                new QueryWrapper<BenefitPoints>()
                        .eq("user_id", userId)
                        // MySQL行锁
                        .last("FOR UPDATE")
        );

        // 账户不存在处理
        if (account == null) {
            // 扣减操作不允许创建新账户
            if (side == 1) {
                throw new BusinessException(ErrorCode.ACCOUNT_NOT_FOUND, "账户不存在，无法扣减");
            }

            // 创建新账户
            BenefitPoints newAccount = new BenefitPoints();
            newAccount.setUserId(userId);
            newAccount.setPoints(pointsChange);
            newAccount.setBalance(balanceChange);

            int result = benefitPointsMapper.insert(newAccount);
            log.info("创建新账户 | 用户:{} | 初始积分:{} | 初始余额:{} | 结果:{}",
                    userId, pointsChange, balanceChange, result);
            return result;
        }

        // 获取当前值
        int currentPoints = account.getPoints();
        BigDecimal currentBalance = account.getBalance();
        int newPoints;
        BigDecimal newBalance;

        // 根据操作类型处理
        String operationType = side == 0 ? "充值" : "扣减";

        if (side == 0) {
            // 充值操作
            newPoints = currentPoints + pointsChange;
            newBalance = currentBalance.add(balanceChange);
        } else {
            // 扣减操作 - 检查余额是否充足
            if (currentPoints < pointsChange) {
                throw new BusinessException(ErrorCode.INSUFFICIENT_POINTS, "积分不足");
            }
            if (currentBalance.compareTo(balanceChange) < 0) {
                throw new BusinessException(ErrorCode.INSUFFICIENT_BALANCE, "余额不足");
            }

            newPoints = currentPoints - pointsChange;
            newBalance = currentBalance.subtract(balanceChange);
        }

        // 更新账户
        account.setPoints(newPoints);
        account.setBalance(newBalance);

        // 记录操作日志
        log.info("账户调账 | 操作:{} | 用户:{} | 原积分:{} | 新积分:{} | 原余额:{} | 新余额:{}",
                operationType, userId, currentPoints, newPoints, currentBalance, newBalance);

        return benefitPointsMapper.updateById(account);
    }
}
