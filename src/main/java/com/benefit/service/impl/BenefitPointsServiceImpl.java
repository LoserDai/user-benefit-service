package com.benefit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.benefit.mapper.BenefitPointsMapper;
import com.benefit.model.entity.BenefitPoints;
import com.benefit.service.BenefitPointsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

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
    public Integer rechargeById(BenefitPoints benefitPoints) {
        // 参数校验
        if (benefitPoints == null || benefitPoints.getUserId() == null) {
            throw new IllegalArgumentException("Invalid benefitPoints parameter");
        }

        Long userId = benefitPoints.getUserId();
        Integer rechargePoints = benefitPoints.getPoints();

        // 查询现有账户
        BenefitPoints account = benefitPointsMapper.selectOne(
                new QueryWrapper<BenefitPoints>().eq("user_id", userId)
        );

        // 账户不存在则创建新账户
        if (account == null) {
            // 创建新账户并设置初始积分
            BenefitPoints newAccount = new BenefitPoints();
            newAccount.setUserId(userId);
            // 新账户初始积分 = 充值积分
            newAccount.setPoints(rechargePoints);

            int insertResult = benefitPointsMapper.insert(newAccount);
            log.info("Created new account for user: {}, initial points: {}, insert result: {}",
                    userId, rechargePoints, insertResult);
            return insertResult;
        }

        // 账户存在时进行充值
        int originalPoints = account.getPoints();
        int newPoints = originalPoints + rechargePoints;

        account.setPoints(newPoints);

        log.info("Recharging user: {} | Original: {} | Added: {} | New total: {}",
                userId, originalPoints, rechargePoints, newPoints);

        return benefitPointsMapper.updateById(account);
    }
}
