package com.benefit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.benefit.common.PageResult;
import com.benefit.exception.BusinessException;
import com.benefit.mapper.BenefitProductMapper;
import com.benefit.model.entity.BenefitProduct;
import com.benefit.model.enums.ErrorCode;
import com.benefit.request.BenefitProductRequest;
import com.benefit.service.BenefitProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


/**
 * @author Allen
 * @date 2025/7/4 11:12
 */

@Service
@Slf4j
public class BenefitProductServiceImpl extends ServiceImpl<BenefitProductMapper, BenefitProduct> implements BenefitProductService {

    @Resource
    private BenefitProductMapper productMapper;

    /**
     * 新增产品
     * @param productRequest
     * @return
     */
    @Override
    public Long insertProduct(BenefitProductRequest productRequest) {

            BenefitProduct product = new BenefitProduct();
            product.setProductName(productRequest.getProductName());
            product.setPrice(productRequest.getPrice());
            product.setRemark(productRequest.getRemark());
            product.setStatus(productRequest.getStatus());

            product.setCreateTime(LocalDateTime.now());
            product.setUpdateTime(LocalDateTime.now());

        try {
            Integer insert = productMapper.insert(product);
            if (insert == 0){
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"create fail");
            }
            log.info("product is {}",product.getProductName());
            return product.getId();
        }catch (Exception e){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 校验产品是否已存在
     * @param productRequest
     * @return
     */
    @Override
    public Boolean isExistProduct(BenefitProductRequest productRequest) {
        BenefitProduct product = new BenefitProduct();
        BeanUtils.copyProperties(productRequest,product);
        BenefitProduct selectOne = productMapper.selectOne(new QueryWrapper<BenefitProduct>().eq("product_name",product.getProductName()));
        if (ObjectUtils.isNull(selectOne)){
            return false;
        }
        return true;
    }

    @Override
    public PageResult<BenefitProduct> queryAllProduct(BenefitProductRequest request) {

        //处理分页参数
        int pageNum = 1;
        int pageSize = 10;
        if (request != null){
            pageNum = Optional.ofNullable(request.getPageNum()).orElse(1);
            pageSize = Optional.ofNullable(request.getPageSize()).orElse(10);

            pageSize = Math.min(pageSize, 100);
        }

        Page<BenefitProduct> page = new Page<>(pageNum, pageSize);

        QueryWrapper<BenefitProduct> queryWrapper = buildQueryWrapper(request);

        Page<BenefitProduct> resultPage = productMapper.selectPage(page, queryWrapper);

        List<BenefitProduct> records = resultPage.getRecords();


        return new PageResult<>(records,
                resultPage.getTotal(),
                (int) resultPage.getCurrent(),
                (int) resultPage.getSize(),
                (int) resultPage.getPages());
    }

    private QueryWrapper<BenefitProduct> buildQueryWrapper(BenefitProductRequest request) {
        QueryWrapper<BenefitProduct> queryWrapper = new QueryWrapper<>();

        if (request == null) {
            queryWrapper.orderByDesc("create_time");
            return queryWrapper;
        }

        // 产品名称模糊查询
        if (StringUtils.hasText(request.getProductName())) {
            queryWrapper.like("product_name", request.getProductName());
        }

        // 状态查询
        if (request.getStatus() != null) {
            queryWrapper.eq("status", request.getStatus());
        }

        // 价格范围查询
        if (request.getPrice() != null) {
            queryWrapper.ge("price", request.getMinPrice());
        }
        if (request.getMaxPrice() != null) {
            queryWrapper.le("price", request.getMaxPrice());
        }

        // 创建时间范围查询
        if (request.getCreateTimeStart() != null) {
            queryWrapper.ge("create_time", request.getCreateTimeStart());
        }
        if (request.getCreateTimeEnd() != null) {
            queryWrapper.le("create_time", request.getCreateTimeEnd());
        }

        // 排序条件
        if (StringUtils.hasText(request.getSortField())) {
            if ("asc".equalsIgnoreCase(request.getSortOrder())) {
                queryWrapper.orderByAsc(request.getSortField());
            } else {
                queryWrapper.orderByDesc(request.getSortField());
            }
        } else {
            // 默认排序
            queryWrapper.orderByDesc("create_time");
        }

        return queryWrapper;
    }
}
