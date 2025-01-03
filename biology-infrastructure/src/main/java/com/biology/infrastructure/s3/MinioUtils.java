package com.biology.infrastructure.s3;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.common.utils.file.FileUploadUtils;

import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MinioUtils {
    private final MinioClient minioClient;

    /**
     * 根据文件路径上传
     *
     * @param subDir 相对应用的基目录
     * @param file   上传的文件
     * @return 文件名称
     */
    public ObjectWriteResponse upload(String subDir, MultipartFile file, String bucketName) {
        try {
            return upload(subDir, file, FileUploadUtils.ALLOWED_EXTENSIONS, bucketName);
        } catch (Exception e) {
            throw new ApiException(Business.UPLOAD_FILE_FAILED, e.getMessage());
        }
    }

    /**
     * 文件上传
     *
     * @param subDir           相对应用的子目录
     * @param file             上传的文件
     * @param allowedExtension 上传文件类型
     * @return 返回上传成功的文件名
     * @throws IOException 比如读写文件出错时
     */
    public ObjectWriteResponse upload(String subDir, MultipartFile file, String[] allowedExtension,
            String bucketName)
            throws Exception {
        FileUploadUtils.isAllowedUpload(file, allowedExtension);
        String fileName = FileUploadUtils.generateFilename(file);
        ObjectWriteResponse objectWriteResponse = minioClient
                .putObject(PutObjectArgs.builder().bucket(bucketName).object(subDir + "/" + fileName)
                        .stream(file.getInputStream(), file.getSize(), -1).build());
        return objectWriteResponse;
    }

    // 预览文件
    public String preview(String bucketName, String objectName) {
        try {
            String url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucketName)
                    .object(objectName).method(Method.GET).expiry(60 * 60).build());
            return url;
        } catch (Exception e) {
            throw new ApiException(Business.PREVIEW_FILE_FAILED, e.getMessage());
        }
    }

    // 下载文件
    public InputStream download(String bucketName, String objectName) {
        try {
            return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            throw new ApiException(Business.DOWNLOAD_FILE_FAILED, e.getMessage());
        }
    }
}
