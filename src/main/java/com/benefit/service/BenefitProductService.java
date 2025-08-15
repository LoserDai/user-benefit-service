package com.benefit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.benefit.common.PageResult;
import com.benefit.model.entity.BenefitProduct;
import com.benefit.request.BenefitProductRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Allen
 * @date 2025/7/4 10:46
 */
public interface BenefitProductService extends IService<BenefitProduct> {

    Long insertProduct(BenefitProductRequest productRequest, MultipartFile file);

    Boolean isExistProduct(BenefitProductRequest productRequest);

    PageResult<BenefitProduct> queryAllProduct(BenefitProductRequest request);

    int deleteProduct(String productId);

    BenefitProduct updateProduct(BenefitProduct product);
}
