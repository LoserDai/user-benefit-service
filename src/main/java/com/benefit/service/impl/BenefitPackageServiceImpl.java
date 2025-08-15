package com.benefit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.benefit.common.PageResult;
import com.benefit.exception.BusinessException;
import com.benefit.mapper.BenefitPackageMapper;
import com.benefit.mapper.BenefitProductMapper;
import com.benefit.mapper.PackageProductRelMapper;
import com.benefit.model.entity.BenefitPackage;
import com.benefit.model.entity.BenefitProduct;
import com.benefit.model.entity.PackageProductRel;
import com.benefit.model.enums.ErrorCode;
import com.benefit.model.enums.Status;
import com.benefit.request.BenefitPackageRequest;
import com.benefit.service.BenefitPackageService;
import com.benefit.service.storage.ImageStorageService;
import com.benefit.vo.BenefitPackageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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

    @Resource
    private ImageStorageService imageStorageService;

    /**
     * 分页查询权益包
     *
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

        return new PageResult<>(
                list,
                page.getTotal(),
                (int) page.getCurrent(),
                (int) page.getSize()
        );
    }

    /**
     * 新增权益包
     *
     * @param request
     * @return
     */
    @Override
    @Transactional
    public int savePackage(BenefitPackageRequest request, MultipartFile imageFile) {

        // 创建权益包: 权益产品只能是已激活的
        List<String> productNames = request.getProductNames();
        BenefitPackage benefitPackage = new BenefitPackage();

        // 创建权益包对象
        try {
            benefitPackage.setPackageName(request.getPackageName());
            benefitPackage.setQuantity(request.getQuantity());
            benefitPackage.setPrice(request.getPrice());
            benefitPackage.setStatus(request.getStatus());
            benefitPackage.setRemark(request.getRemark());
            String morePath = "benefit-packages/";
            benefitPackage.setCreateTime(LocalDateTime.now());
            benefitPackage.setUpdateTime(LocalDateTime.now());
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = imageStorageService.storeBenefitProductImage(imageFile,morePath);
                benefitPackage.setPackageImagePath(imageUrl);
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"save packageImagePath error!");
        }

        //查询是否有重名的权益包,如果有,不让新增
        Long isExist = benefitPackageMapper.selectCount(new QueryWrapper<BenefitPackage>().eq("package_name", request.getPackageName()));
        if (isExist > 0) {
            log.info("This package_name is exist!");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "This package_name is exist!");
        }

        // 插入权益包
        int count = benefitPackageMapper.insert(benefitPackage);
        if (count <= 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "save package failed.");
        }
        log.info("save Package success,PackageName is : {}", benefitPackage.getPackageName());

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

    /**
     * 更新权益包,先删除再插入新的
     *
     * @param request
     * @return
     */
    @Override
    @Transactional
    public int updatePackage(BenefitPackageRequest request) {

        //修改benefit_package的数据
        BenefitPackage benefitPackage = new BenefitPackage();

        benefitPackage.setRemark(request.getRemark());
        benefitPackage.setPackageName(request.getPackageName());
        benefitPackage.setPrice(request.getPrice());
        benefitPackage.setStatus(request.getStatus());
        benefitPackage.setUpdateTime(LocalDateTime.now());
        benefitPackage.setQuantity(request.getQuantity());

        Long isExist = benefitPackageMapper.selectCount(new QueryWrapper<BenefitPackage>().eq("id", request.getId()));

        if (isExist <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "This packageName is not exist !");
        }

        //TODO 判断传进来的权益包参数是否有修改, 如果没有就不调方法修改
        int updateBp = benefitPackageMapper.update(benefitPackage, new QueryWrapper<BenefitPackage>()
                .eq("id", request.getId()));

        //修改package_product_rel表中的productId
        //先删除旧的关联数据再插入新的
        int deleteCount = packageProductRelMapper.delete(new QueryWrapper<PackageProductRel>()
                .eq("package_id", request.getId()));
        log.info("It has been deleted {} relData", deleteCount);

        //将关联的权益产品清除为0时,就不插入新数据了
        // 获取 productNames 并过滤掉空字符串和纯空格字符串
        List<String> productNames = Optional.ofNullable(request.getProductNames())
                .orElse(Collections.emptyList())
                .stream()
                .filter(name -> name != null && !name.trim().isEmpty())
                .collect(Collectors.toList());

        // 判断过滤后的集合是否为空
        if (productNames.isEmpty()) {
            return 1;
        }
        //获取productName的ids
        List<Long> list = new ArrayList<>();
        for (String product : request.getProductNames()) {
            BenefitProduct bp = benefitProductMapper.selectOne(
                    new QueryWrapper<BenefitProduct>().eq("product_name", product).eq("status", Status.ACTIVE));

            if (bp == null) {
                log.info("Product {} is not activated or does not exist.", product);
                continue;
            }

            Long productId = bp.getId();
            list.add(productId);
        }

        return packageProductRelMapper.insertPackageProductRelBatch(request.getId(), list);
    }

}
