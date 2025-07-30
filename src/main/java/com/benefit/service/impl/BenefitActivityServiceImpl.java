package com.benefit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.benefit.exception.BusinessException;
import com.benefit.mapper.ActivityPackageRelMapper;
import com.benefit.mapper.BenefitActivityMapper;
import com.benefit.mapper.BenefitPackageMapper;
import com.benefit.model.entity.BenefitActivity;
import com.benefit.model.enums.ErrorCode;
import com.benefit.model.enums.Status;
import com.benefit.request.BenefitActivityRequest;
import com.benefit.service.BenefitActivityService;
import com.benefit.vo.BenefitPackageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Allen
 * @date 2025/7/4 11:08
 */

@Service
@Slf4j
public class BenefitActivityServiceImpl extends ServiceImpl<BenefitActivityMapper, BenefitActivity> implements BenefitActivityService {


    @Resource
    private BenefitActivityMapper benefitActivityMapper;


    @Resource
    private BenefitPackageMapper benefitPackageMapper;

    @Resource
    private ActivityPackageRelMapper activityPackageRelMapper;

    /**
     * 新增权益活动
     * @param request
     * @return Integer
     */
    @Override
    public int saveActivity(BenefitActivityRequest request) {

        //创建权益活动, 权益包只能是已激活的
        BenefitActivity activity = new BenefitActivity();

        activity.setActivityName(request.getActivityName());
        activity.setActivityType(request.getActivityType());
        activity.setStatus(request.getStatus());
        activity.setStartTime(request.getStartTime());
        activity.setEndTime(request.getEndTime());
        activity.setDiscountValue(request.getDiscountValue());
        activity.setPrice(request.getPrice());
        activity.setMinPurchase(request.getMinPurchase());
        activity.setPurchaseLimit(request.getPurchaseLimit());

        //不一定传了的参数
        if (!request.getDescription().isEmpty()) {
            activity.setDescription(request.getDescription());
        }
        if (!request.getRemark().isEmpty()) {
            activity.setRemark(request.getRemark());
        }

        activity.setCreateTime(LocalDateTime.now());
        activity.setUpdateTime(LocalDateTime.now());

        //查询是否有重名的权益活动,如果有,不让新增
        Long isExist = benefitActivityMapper.selectCount(new QueryWrapper<BenefitActivity>().eq("activity_name", request.getActivityName()));
        if (isExist > 0) {
            log.info("This activity_name is exist!");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "This activity_name is exist!");
        }

        //插入数据
        int insertCount = benefitActivityMapper.insert(activity);
        if (insertCount <= 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "save activity failed.");
        }
        log.info("save activity success,activity_name is : {}", activity.getActivityName());

        //获取权益活动的ID
        Long activityId = activity.getId();

        //packageIds
        // 判断包是否已激活，并收集包ID
        List<String> packageIds = request.getPackageIds();

        List<Long> packageIdList = new ArrayList<>();
        for (String packageId : packageIds) {

            BenefitPackageVo one = benefitPackageMapper.selectOneByIdAndStatus(Status.ACTIVE,packageId);
            if (one == null) {
                log.info("package: {} is not activated or does not exist.", packageId);
                continue;
            }

            Long productId = one.getId();
            packageIdList.add(productId);
        }
        // 批量插入关联关系
        if (!packageIdList.isEmpty()) {
            int successCount = activityPackageRelMapper.insertPackageProductRelBatch(activityId, packageIdList);
            if (successCount != packageIdList.size()) {
                log.error("Some equity packages failed to be associated, expected: {}, actual: {}", packageIdList.size(), successCount);
            }
        }
        log.info("Successfully associated {} benefit packages to activity ID: {}", packageIdList.size(), activityId);
        return packageIdList.size();
    }

    @Override
    public int updateActivity(BenefitActivityRequest request) {
        return 0;
    }
}
