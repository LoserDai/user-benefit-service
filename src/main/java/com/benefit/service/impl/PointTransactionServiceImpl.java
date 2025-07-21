package com.benefit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.benefit.common.ResultUtils;
import com.benefit.exception.BusinessException;
import com.benefit.mapper.PointTransactionMapper;
import com.benefit.model.entity.PointTransaction;
import com.benefit.model.enums.ErrorCode;
import com.benefit.service.PointTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Allen
 * @date 2025/7/18 17:02
 */

@Service
@Slf4j
public class PointTransactionServiceImpl extends ServiceImpl<PointTransactionMapper, PointTransaction> implements PointTransactionService {

    @Resource
    private PointTransactionMapper transactionMapper;

    @Transactional
    @Override
    public Integer savePointTrans(PointTransaction pointTransaction) {

        log.info("bizCode is {}", pointTransaction.getBizId());

        int count = 0;
        try {
            Set<Integer> set = new HashSet<>(Arrays.asList(1, 2, 3, 4));
            if (!set.contains(pointTransaction.getChangeType())) {
                log.info("Error changeType: {}", pointTransaction.getChangeType());
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "Wrong changeType");
            }
            count = transactionMapper.insert(pointTransaction);

            if (count <= 0) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "savePointTrans failed");
            }
        } catch (Exception e) {
            log.info("Exception: ", e);
        }
        return count;
    }
}
