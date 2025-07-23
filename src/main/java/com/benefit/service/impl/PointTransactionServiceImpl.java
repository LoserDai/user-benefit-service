package com.benefit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.benefit.common.PageResult;
import com.benefit.converter.PointTransactionConverter;
import com.benefit.exception.BusinessException;
import com.benefit.mapper.PointTransactionMapper;
import com.benefit.mapper.UserMapper;
import com.benefit.model.entity.BenefitProduct;
import com.benefit.model.entity.PointTransaction;
import com.benefit.model.entity.User;
import com.benefit.model.enums.ErrorCode;
import com.benefit.request.PointTransactionRequest;
import com.benefit.service.PointTransactionService;
import com.benefit.service.UserService;
import com.benefit.vo.PointTransactionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Allen
 * @date 2025/7/18 17:02
 */

@Service
@Slf4j
public class PointTransactionServiceImpl extends ServiceImpl<PointTransactionMapper, PointTransaction> implements PointTransactionService {


    @Resource
    private PointTransactionMapper transactionMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

    /**
     * 保存交易流水
     * @param pointTransaction
     * @return
     */
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

    /**
     * 分页查询交易流水
     * @param request
     * @return
     */
    @Override
    public PageResult<PointTransactionVo> queryAllPointTransaction(PointTransactionRequest request) {
        // 1. 初始化分页参数
        int pageNum = Optional.ofNullable(request)
                .map(PointTransactionRequest::getPageNum)
                .orElse(1);
        int pageSize = Optional.ofNullable(request)
                .map(PointTransactionRequest::getPageSize)
                .filter(size -> size <= 100)
                .orElse(10);

        // 2. 构建查询条件
        QueryWrapper<PointTransaction> wrapper = buildQueryWrapper(request);
        Page<PointTransaction> page = new Page<>(pageNum, pageSize);

        // 3. 执行分页查询
        Page<PointTransaction> resultPage = transactionMapper.selectPage(page, wrapper);

        // 4. 无数据时返回空分页结果
        if (resultPage.getRecords() == null || resultPage.getRecords().isEmpty()) {
            log.info("No point transaction records found for request: {}", request);
            return new PageResult<>(
                    Collections.emptyList(),
                    resultPage.getTotal(),
                    pageNum,
                    pageSize
            );
        }

        // 5. 批量获取用户账户信息
        Map<Long, String> userAccountMap;
        String requestAccount = Optional.ofNullable(request)
                .map(PointTransactionRequest::getAccount)
                .orElse(null);

        // 当请求中没有提供 account 时，批量查询用户信息
        if (requestAccount == null) {
            // 5.1 收集所有用户ID
            Set<Long> userIds = resultPage.getRecords().stream()
                    .map(PointTransaction::getUserId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            // 5.2 批量查询用户账户信息
            if (!userIds.isEmpty()) {
                userAccountMap = userService.getUserAccountMap(userIds);
            } else {
                userAccountMap = Collections.emptyMap();
            }
        } else {
            userAccountMap = Collections.emptyMap();
        }

        // 6. 转换VO并补充账户信息
        PointTransactionConverter converter = new PointTransactionConverter();
        List<PointTransactionVo> vos = resultPage.getRecords().stream()
                .map(record -> {
                    // 如果请求中有account则使用请求的，否则从批量查询结果中获取
                    String account = requestAccount;
                    if (account == null) {
                        account = userAccountMap.getOrDefault(record.getUserId(), "");
                    }
                    return converter.toVO(record, account);
                })
                .collect(Collectors.toList());

        // 7. 构造分页响应
        return new PageResult<>(
                vos,
                resultPage.getTotal(),
                pageNum, pageSize
        );
    }

    /**
     * 组装查询条件
     * @param request
     * @return
     */
    private QueryWrapper<PointTransaction> buildQueryWrapper(PointTransactionRequest request){
        QueryWrapper<PointTransaction> wrapper = new QueryWrapper<>();

        if (request == null){
            wrapper.orderByDesc("create_time");
            return wrapper;
        }

        // 用户ID查询
        if (request.getUserId() != null) {
            wrapper.eq("user_id",request.getUserId());
        }

        // 用户名称模糊查询
        if (request.getAccount() != null) {
            User user = userMapper.selectOne(new QueryWrapper<User>().eq("account", request.getAccount()));
            if (ObjectUtils.isNull(user)){
                throw new BusinessException(ErrorCode.SYSTEM_ERROR,"user is null");
            }
            wrapper.eq("user_id",user.getId());
        }

        //交易类型
        if (request.getChangeType() != null){
            wrapper.eq("change_type",request.getChangeType());
        }

        //业务ID(订单号)
        if (request.getBizId() != null){
            wrapper.eq("biz_id",request.getBizId());
        }

        //创建时间
        if (request.getCreateTime() != null) {
            wrapper.ge("create_time", request.getCreateTime());
        }
        if (request.getCreateTime() != null) {
            wrapper.le("create_time", request.getCreateTime());
        }

        //默认排序
        wrapper.orderByDesc("create_time");

        return wrapper;
    }
}
