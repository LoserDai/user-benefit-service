package com.benefit.controller;

import com.benefit.service.BenefitProductService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Allen
 * @date 2025/7/4 10:44
 */
@RestController
@RequestMapping("/product")
@Api(tags = "权益产品操作入口")
@Slf4j
public class BenefitProductController {

    @Resource
    private BenefitProductService benefitProductService;
}
