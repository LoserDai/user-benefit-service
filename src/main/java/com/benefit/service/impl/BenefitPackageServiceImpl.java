package com.benefit.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.benefit.common.PageResult;
import com.benefit.mapper.BenefitPackageMapper;
import com.benefit.model.entity.BenefitPackage;
import com.benefit.request.BenefitPackageRequest;
import com.benefit.service.BenefitPackageService;
import com.benefit.vo.BenefitPackageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author Allen
 * @date 2025/7/4 11:09
 */
@Service
@Slf4j
public class BenefitPackageServiceImpl extends ServiceImpl<BenefitPackageMapper, BenefitPackage> implements BenefitPackageService {

    @Resource
    private BenefitPackageMapper benefitPackageMapper;

    /**
     * 分页查询权益包
     * @param request
     * @return
     */
    @Override
    public PageResult<BenefitPackageVo> queryPackage(BenefitPackageRequest request) {

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

}
