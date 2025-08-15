package com.benefit.service.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @Author Allen
 * @Date 2025/8/14 17:25
 * @Description
 */
@Service
public class ImageStorageService {

    @Value("${image.storage.path}")
    private String baseStoragePath;

    @Value("${image.access.url}")
    private String baseAccessUrl;

    /**
     * 存储权益产品图片
     *
     * @param imageFile 上传的图片文件
     * @return 图片访问URL
     * @throws IOException 文件存储异常
     */
    public String storeBenefitProductImage(MultipartFile imageFile,String morePath) throws IOException {
        // 1. 校验文件合法性
        validateImageFile(imageFile);

        // 2. 构建完整存储路径（使用 Paths 安全拼接，避免修改成员变量）
        // 基础路径 + 额外路径（如 "basePath/benefitProduct/"）
        Path fullStoragePath = Paths.get(baseStoragePath, morePath);

        // 3. 确保存储目录存在（不存在则创建）
        if (!Files.exists(fullStoragePath)) {
            Files.createDirectories(fullStoragePath);
        }

        // 4. 生成唯一文件名（处理边界情况）
        String uniqueFileName = generateUniqueFileName(imageFile.getOriginalFilename());

        // 5. 保存文件（Files.copy 会自动关闭输入流，无需手动处理）
        Path filePath = fullStoragePath.resolve(uniqueFileName);
        Files.copy(imageFile.getInputStream(), filePath);

        // 6. 构建访问URL（安全拼接，确保路径正确）
        return buildAccessUrl(baseAccessUrl, morePath, uniqueFileName);
    }

    /**
     * 删除权益产品图片
     *
     * @param imageUrl 图片访问URL
     * @return 是否删除成功
     */
    public boolean deleteBenefitProductImage(String imageUrl) {
        try {
            // 从URL中提取文件名
            String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            Path filePath = Paths.get(baseStoragePath, fileName);

            if (Files.exists(filePath)) {
                Files.delete(filePath);
                return true;
            }
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    /**
    * @Description: 校验图片是否合法
    * @Param: [file]
    * @Return: void
    * @Author: Allen
    */
    private void validateImageFile(MultipartFile file) {
        // 校验文件不为空
        if (file.isEmpty()) {
            throw new IllegalArgumentException("上传的图片文件不能为空");
        }

        // 校验文件类型（ContentType）
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("上传的文件必须是图片类型（支持 jpg、png、jpeg 等）");
        }

        // 校验文件名不为空
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.trim().isEmpty()) {
            throw new IllegalArgumentException("文件名为空，无法解析扩展名");
        }
    }

    /**
    * @Description: 生成唯一文件名（处理无扩展名的情况）
    * @Param: [originalFilename]
    * @Return: java.lang.String
    * @Author: Allen
    */
    private String generateUniqueFileName(String originalFilename) {
        // 提取扩展名（若无扩展名，默认用 .png）
        int extIndex = originalFilename.lastIndexOf(".");
        String fileExtension = (extIndex > 0) ? originalFilename.substring(extIndex) : ".png";

        // 生成UUID + 扩展名（确保唯一性）
        return UUID.randomUUID() + fileExtension;
    }

    /**
    * @Description: 安全拼接访问URL（处理斜杠问题）
    * @Param: [baseUrl, morePath, fileName]
    * @Return: java.lang.String
    * @Author: Allen
    */
    private String buildAccessUrl(String baseUrl, String morePath, String fileName) {
        // 确保 baseUrl 以斜杠结尾，morePath 不以斜杠开头/结尾，避免重复
        String normalizedBaseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
        // 去除首尾斜杠
        String normalizedMorePath = morePath.replaceAll("^/+", "").replaceAll("/+$", "");
        String pathSeparator = normalizedMorePath.isEmpty() ? "" : "/";

        return normalizedBaseUrl + normalizedMorePath + pathSeparator + fileName;
    }
}
