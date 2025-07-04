package com.benefit.controller;

import com.benefit.service.BenefitPackageService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Allen
 * @date 2025/7/4 10:51
 */

@RestController
@RequestMapping("/package")
@Api(tags = "权益包操作入口")
@Slf4j
public class BenefitPackageController {

    @Resource
    private BenefitPackageService benefitPackageService;
}
