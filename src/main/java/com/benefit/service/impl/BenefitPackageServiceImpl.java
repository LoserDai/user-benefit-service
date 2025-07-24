package com.benefit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.benefit.common.PageResult;
import com.benefit.common.ResultUtils;
import com.benefit.exception.BusinessException;
import com.benefit.mapper.BenefitPackageMapper;
import com.benefit.mapper.BenefitProductMapper;
import com.benefit.mapper.PackageProductRelMapper;
import com.benefit.model.entity.BenefitPackage;
import com.benefit.model.entity.BenefitProduct;
import com.benefit.model.enums.ErrorCode;
import com.benefit.model.enums.Status;
import com.benefit.request.BenefitPackageRequest;
import com.benefit.service.BenefitPackageService;
import com.benefit.vo.BenefitPackageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Allen
 * @date 2025/7/4 11:09
 */
@Service
@Slf4j
public class BenefitPackageServiceImpl extends ServiceImpl<BenefitPackageMapper, BenefitPackage> implements BenefitPackageService {

    @Resource
    private BenefitPackageMapper benefitPackageMapper;

    @Resource
    private BenefitProductMapper benefitProductMapper;

    @Resource
    private PackageProductRelMapper packageProductRelMapper;

    /**
     * 分页查询权益包
     * @param request
     * @return
     */
    @Override
    public PageResult<BenefitPackageVo> queryPackage(BenefitPackageRequest request) {

        // 预处理参数：过滤 productNames 中的空字符串
        if (request != null && request.getProductNames() != null) {
            List<String> filteredNames = request.getProductNames().stream()
                    .filter(name -> name != null && !name.trim().isEmpty())
                    .collect(Collectors.toList());
            request.setProductNames(filteredNames);
        }

        int pageNum = Optional.ofNullable(request)
                .map(BenefitPackageRequest::getPageNum)
                .filter(num -> num > 0)
                .orElse(1);

        int pageSize = Optional.ofNullable(request)
                .map(BenefitPackageRequest::getPageSize)
                .filter(size -> size > 0 && size <= 500)
                .orElse(10);

        Page<BenefitPackageVo> page = new Page<>(pageNum, pageSize);

        List<BenefitPackageVo> list = benefitPackageMapper.selectPackageList(page, request);

        // 返回分页结果，封装分页信息
        return new PageResult<>(
                list,
                page.getTotal(),
                (int) page.getCurrent(),
                (int) page.getSize()
        );
    }

    /**
     * 新增权益包
     * @param request
     * @return
     */
    @Override
    @Transactional
    public int savePackage(BenefitPackageRequest request) {

        // 创建权益包: 权益产品只能是已激活的
        List<String> productNames = request.getProductNames();

        // 创建权益包对象
        BenefitPackage benefitPackage = new BenefitPackage();
        benefitPackage.setPackageName(request.getPackageName());
        benefitPackage.setQuantity(request.getQuantity());
        benefitPackage.setPrice(request.getPrice());
        benefitPackage.setStatus(request.getStatus());
        benefitPackage.setRemark(request.getRemark());

        benefitPackage.setCreateTime(LocalDateTime.now());
        benefitPackage.setUpdateTime(LocalDateTime.now());

        //查询是否有重名的权益包,如果有,不让新增
        Long isExist = benefitPackageMapper.selectCount(new QueryWrapper<BenefitPackage>().eq("package_name", request.getPackageName()));
        if (isExist > 0){
            log.info("This package_name is exist!");
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"This package_name is exist!");
        }

        // 插入权益包
        int count = benefitPackageMapper.insert(benefitPackage);
        if (count <= 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "save package failed.");
        }
        log.info("save Package success,PackageName is : {}",benefitPackage.getPackageName());

        // 获取该权益包的ID
        Long packageId = benefitPackage.getId();

        // 判断产品是否已激活，并收集产品ID
        List<Long> productIds = new ArrayList<>();
        for (String product : productNames) {
            BenefitProduct bp = benefitProductMapper.selectOne(
                    new QueryWrapper<BenefitProduct>().eq("product_name", product).eq("status", Status.ACTIVE));

            if (bp == null) {
                log.info("Product {} is not activated or does not exist.", product);
                continue;
            }

            Long productId = bp.getId();
            productIds.add(productId);
        }

        // 批量插入关联关系，减少数据库的交互
        if (!productIds.isEmpty()) {
            // 批量插入
            int successCount = packageProductRelMapper.insertPackageProductRelBatch(packageId, productIds);
            if (successCount != productIds.size()) {
                log.error("Failed to insert some products into package_product_rel");
            }
        }
        return productIds.size();
    }

}
