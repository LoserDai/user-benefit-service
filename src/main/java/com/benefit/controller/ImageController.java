package com.benefit.controller;

import com.benefit.common.BaseResponse;
import com.benefit.common.ResultUtils;
import com.benefit.model.enums.ErrorCode;
import com.benefit.service.storage.ImageStorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

/**
 * @Author Allen
 * @Date 2025/8/14 17:26
 * @Description
 */
@RestController
@Api(tags = "权益图片上传入口")
@RequestMapping("/images")
public class ImageController {
    private final ImageStorageService imageStorageService;

    public ImageController(ImageStorageService imageStorageService) {
        this.imageStorageService = imageStorageService;
    }

    /**
     * 上传权益产品图片
     *
     * @param file 图片文件
     * @return 图片访问URL
     */
    @PostMapping("/benefit-products")
    @ApiOperation("新增权益图片")
    public BaseResponse uploadBenefitProductImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"Please check the file!");
        }

        try {
            // 检查文件类型
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResultUtils.error(ErrorCode.PARAMS_ERROR,"Please upload picture!");
            }

            // 存储图片
            String imageUrl = imageStorageService.storeBenefitProductImage(file);
            return ResultUtils.success(Collections.singletonMap("imageUrl", imageUrl));
        } catch (IOException e) {
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"Picture upload failed!");
        }
    }
}
