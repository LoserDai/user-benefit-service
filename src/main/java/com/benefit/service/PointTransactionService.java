package com.benefit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.benefit.common.PageResult;
import com.benefit.model.entity.PointTransaction;
import com.benefit.request.PointTransactionRequest;
import com.benefit.vo.PointTransactionVo;

/**
 * @author Allen
 * @date 2025/7/18 17:01
 */
public interface PointTransactionService extends IService<PointTransaction> {
    Integer savePointTrans(PointTransaction pointTransaction);

    PageResult<PointTransactionVo> queryAllPointTransaction(PointTransactionRequest request);
}
