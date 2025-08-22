package com.benefit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.benefit.exception.BusinessException;
import com.benefit.mapper.BenefitPointsMapper;
import com.benefit.mapper.PointTransactionMapper;
import com.benefit.model.entity.BenefitPoints;
import com.benefit.model.entity.PointTransaction;
import com.benefit.model.enums.ErrorCode;
import com.benefit.request.BenefitPointsRequest;
import com.benefit.service.BenefitPointsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Resource
    private PointTransactionMapper pointTransactionMapper;


    /**
    * @Description: 调账
    * @Param: [request]
    * @Return: int
    * @Author: Allen
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyBalance(BenefitPointsRequest request) {
        // 参数校验增强
        if (request == null || request.getUserId() == null) {
            throw new IllegalArgumentException("请求参数不能为空");
        }

        Long userId = request.getUserId();
        Integer side = request.getSide();
        BigDecimal pointsChange = request.getPoints();
        BigDecimal balanceChange = request.getBalance();

        // 校验操作类型
        if (side == null || (side != 0 && side != 1)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "无效的操作类型");
        }

        // 校验金额有效性
        if (pointsChange == null || pointsChange.compareTo(BigDecimal.ZERO) <0 ||
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
        BigDecimal currentPoints = account.getPoints();
        BigDecimal currentBalance = account.getBalance();
        BigDecimal newPoints;
        BigDecimal newBalance;

        // 根据操作类型处理
        String operationType = side == 0 ? "充值" : "扣减";

        if (side == 0) {
            // 充值操作
            newPoints = currentPoints.add(pointsChange);
            newBalance = currentBalance.add(balanceChange);
        } else {
            // 扣减操作 - 检查余额是否充足
            if (currentPoints.compareTo(pointsChange)<0) {
                throw new BusinessException(ErrorCode.INSUFFICIENT_POINTS, "积分不足");
            }
            if (currentBalance.compareTo(balanceChange) < 0 ) {
                throw new BusinessException(ErrorCode.INSUFFICIENT_BALANCE, "余额不足");
            }

            newPoints = currentPoints.subtract(pointsChange);
            newBalance = currentBalance.subtract(balanceChange);
        }

        // 更新账户
        account.setPoints(newPoints);
        account.setBalance(newBalance);

        // 记录操作日志
        log.info("账户调账 | 操作:{} | 用户:{} | 原积分:{} | 新积分:{} | 原余额:{} | 新余额:{}",
                operationType, userId, currentPoints, newPoints, currentBalance, newBalance);

        int count = benefitPointsMapper.updateById(account);
        //保存交易流水
        PointTransaction pointTransaction = new PointTransaction();
        pointTransaction.setUserId(request.getUserId());

        if (request.getSide() == 0) {
            pointTransaction.setChangeType(1);
        }
        if (request.getSide() == 1) {
            pointTransaction.setChangeType(2);
        }

        pointTransaction.setChangePoint(request.getPoints());
        pointTransaction.setChangeBalance(request.getBalance());

        pointTransaction.setPointsAfter(newPoints);
        pointTransaction.setBalanceAfter(newBalance);

        if (request.getType() == 1) {
            pointTransaction.setRemark("兑换消费");
        }
        if (request.getType() == 2) {
            pointTransaction.setRemark("账户调账");
        }
        if (request.getType() == 3) {
            pointTransaction.setRemark("购买消费");
        }

        int insert = pointTransactionMapper.insert(pointTransaction);
        log.info("save pointTransaction: {}",pointTransaction);
        return count;
    }
}
