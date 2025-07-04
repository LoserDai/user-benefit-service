package com.benefit.controller;

import com.benefit.service.BenefitPackageService;
import com.benefit.service.BenefitPointsService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Allen
 * @date 2025/7/4 10:59
 */
@RestController
@RequestMapping("/points")
@Api(tags = "权益积分操作入口")
@Slf4j
public class BenefitPointsController {

    @Resource
    private BenefitPointsService benefitPointsService;
}
