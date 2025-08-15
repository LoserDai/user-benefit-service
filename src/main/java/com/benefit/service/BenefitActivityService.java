package com.benefit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.benefit.common.PageResult;
import com.benefit.model.entity.BenefitActivity;
import com.benefit.request.BenefitActivityRequest;
import com.benefit.vo.BenefitActivityVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Allen
 * @date 2025/7/4 10:54
 */
public interface BenefitActivityService extends IService<BenefitActivity> {
    int saveActivity(BenefitActivityRequest request, MultipartFile file);

    int updateActivity(BenefitActivityRequest request);

    PageResult<BenefitActivityVo> queryActivityList(BenefitActivityRequest request);
}
