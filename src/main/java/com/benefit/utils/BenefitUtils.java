package com.benefit.utils;



import com.benefit.model.entity.BenefitPoints;

import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * @author Allen
 * @date 2025/6/10 17:10
 * 用于操作积分和余额的工具类
 */

public class BenefitUtils {
    // 使用可重入锁保证线程安全
    private static final Lock lock = new ReentrantLock();

    /**
     * 充值积分和余额
     *
     * @param points      目标实体
     * @param pointsToAdd 增加的积分数
     * @param amountToAdd 增加的余额
     * @return 操作后的实体
     * @throws IllegalArgumentException 如果参数为负
     */

    public static BenefitPoints recharge(BenefitPoints points, int pointsToAdd, int amountToAdd) {
        //校验积分和余额是否为负数
        validateNonNegative(pointsToAdd, amountToAdd);

        lock.lock();
        try {
            points.setPoints(points.getPoints() + pointsToAdd);
            points.setBalance(points.getBalance() + amountToAdd);
            return points;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 扣减积分和余额
     *
     * @param points         目标实体
     * @param pointsToDeduct 扣减的积分数
     * @param amountToDeduct 扣减的余额
     * @return 操作后的实体
     * @throws InsufficientFundsException 如果余额或积分不足
     * @throws IllegalArgumentException   如果参数为负
     */
    public static BenefitPoints deduct(BenefitPoints points, int pointsToDeduct, int amountToDeduct)
            throws InsufficientFundsException {

        //校验积分和余额是否为负数
        validateNonNegative(pointsToDeduct, amountToDeduct);

        lock.lock();
        try {
            if (points.getPoints() < pointsToDeduct) {
                throw new InsufficientFundsException("积分不足: 需要 " + pointsToDeduct +
                        ", 可用 " + points.getPoints());
            }

            if (points.getBalance() < amountToDeduct) {
                throw new InsufficientFundsException("余额不足: 需要 " + amountToDeduct +
                        ", 可用 " + points.getBalance());
            }

            points.setPoints(points.getPoints() - pointsToDeduct);
            points.setBalance(points.getBalance() - amountToDeduct);
            return points;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 退款操作
     *
     * @param points         目标实体
     * @param pointsToRefund 退还的积分数
     * @param amountToRefund 退还的余额
     * @return 操作后的实体
     * @throws IllegalArgumentException 如果参数为负
     */
    public static BenefitPoints refund(BenefitPoints points, int pointsToRefund, int amountToRefund) {
        // 退款本质是充值操作
        return recharge(points, pointsToRefund, amountToRefund);
    }

    /**
     * 冻结积分和余额
     *
     * @param points         目标实体
     * @param pointsToFreeze 冻结的积分数
     * @param amountToFreeze 冻结的余额
     * @return 冻结后的实体和冻结记录
     * @throws InsufficientFundsException 如果余额或积分不足
     * @throws IllegalArgumentException   如果参数为负
     */
    public static FreezeResult freeze(BenefitPoints points, int pointsToFreeze, int amountToFreeze)
            throws InsufficientFundsException {

        //校验积分和余额是否为负数
        validateNonNegative(pointsToFreeze, amountToFreeze);

        lock.lock();
        try {
            if (points.getPoints() < pointsToFreeze) {
                throw new InsufficientFundsException("积分不足: 需要冻结 " + pointsToFreeze +
                        ", 可用 " + points.getPoints());
            }

            if (points.getBalance().compareTo(amountToFreeze) < 0) {
                throw new InsufficientFundsException("余额不足: 需要冻结 " + amountToFreeze +
                        ", 可用 " + points.getBalance());
            }

            // 执行冻结操作
            points.setPoints(points.getPoints() - pointsToFreeze);
            points.setBalance(points.getBalance()- amountToFreeze);

            // 创建冻结记录
            FreezeRecord freezeRecord = new FreezeRecord(pointsToFreeze, amountToFreeze);

            return new FreezeResult(points, freezeRecord);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 解冻积分和余额
     *
     * @param points       目标实体
     * @param freezeRecord 冻结记录
     * @return 解冻后的实体
     */
    public static BenefitPoints unfreeze(BenefitPoints points, FreezeRecord freezeRecord) {
        lock.lock();
        try {
            points.setPoints(points.getPoints() + freezeRecord.getFrozenPoints());
            points.setBalance(points.getBalance() + freezeRecord.getFrozenAmount());
            return points;
        } finally {
            lock.unlock();
        }
    }

    // 验证参数非负
    private static void validateNonNegative(int points, int amount) {
        if (points < 0) {
            throw new IllegalArgumentException("积分不能为负: " + points);
        }
        if (amount < 0) {
            throw new IllegalArgumentException("金额不能为负: " + amount);
        }
    }

    // 自定义异常：资金不足
    public static class InsufficientFundsException extends Exception {
        public InsufficientFundsException(String message) {
            super(message);
        }
    }

    // 冻结记录实体
    public static class FreezeRecord {
        private final int frozenPoints;
        private final int frozenAmount;

        public FreezeRecord(int frozenPoints, int frozenAmount) {
            this.frozenPoints = frozenPoints;
            this.frozenAmount = frozenAmount;
        }

        public int getFrozenPoints() {
            return frozenPoints;
        }

        public int getFrozenAmount() {
            return frozenAmount;
        }
    }

    // 冻结操作返回结果
    public static class FreezeResult {
        private final BenefitPoints updatedPoints;
        private final FreezeRecord freezeRecord;

        public FreezeResult(BenefitPoints updatedPoints, FreezeRecord freezeRecord) {
            this.updatedPoints = updatedPoints;
            this.freezeRecord = freezeRecord;
        }

        public BenefitPoints getUpdatedPoints() {
            return updatedPoints;
        }

        public FreezeRecord getFreezeRecord() {
            return freezeRecord;
        }
    }
}
