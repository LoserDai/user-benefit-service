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
    private String storagePath;

    @Value("${image.access.url}")
    private String accessUrl;

    /**
     * 存储权益产品图片
     *
     * @param imageFile 上传的图片文件
     * @return 图片访问URL
     * @throws IOException 文件存储异常
     */
    public String storeBenefitProductImage(MultipartFile imageFile) throws IOException {
        // 确保存储目录存在
        Path storageDir = Paths.get(storagePath);
        if (!Files.exists(storageDir)) {
            Files.createDirectories(storageDir);
        }

        // 生成唯一文件名 (UUID + 原扩展名)
        String originalFilename = imageFile.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFileName = UUID.randomUUID() + fileExtension;

        // 构建存储路径
        Path filePath = storageDir.resolve(uniqueFileName);

        // 保存文件
        Files.copy(imageFile.getInputStream(), filePath);

        // 返回访问URL
        return accessUrl + uniqueFileName;
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
            Path filePath = Paths.get(storagePath, fileName);

            if (Files.exists(filePath)) {
                Files.delete(filePath);
                return true;
            }
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}
