package com.benefit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.benefit.mapper.PointTransactionMapper;
import com.benefit.model.entity.PointTransaction;
import com.benefit.service.PointTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Allen
 * @date 2025/7/18 17:02
 */

@Service
@Slf4j
public class PointTransactionServiceImpl extends ServiceImpl<PointTransactionMapper, PointTransaction> implements PointTransactionService {
}
