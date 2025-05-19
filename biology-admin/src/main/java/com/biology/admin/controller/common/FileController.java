package com.biology.admin.controller.common;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.extra.servlet.ServletUtil;

import com.biology.common.constant.Constants.UploadSubDir;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.common.utils.ServletHolderUtil;
import com.biology.common.utils.file.FileUploadUtils;
import com.biology.common.utils.jackson.JacksonUtil;
import com.biology.domain.common.dto.UploadDTO;
import com.biology.infrastructure.config.minio.MinioConfig;
import com.biology.infrastructure.s3.MinioUtils;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.http.Method;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 通用请求处理
 * TODO 需要重构
 * 
 * @author valarchie
 */
@Tag(name = "上传API", description = "上传相关接口")
@RestController
@RequestMapping("/file")
@Slf4j
@RequiredArgsConstructor
public class FileController {

    private final MinioConfig minioConfig;

    private final MinioUtils minioUtils;

    /**
     * 通用下载请求
     * download接口 其实不是很有必要
     * 
     * @param fileName 文件名称
     */
    @Operation(summary = "下载文件")
    @GetMapping("/download")
    public ResponseEntity<byte[]> fileDownload(String fileName, HttpServletResponse response) {
        try {

            InputStream inputStream = minioUtils.download(minioConfig.getBucketName(), fileName);
            HttpHeaders downloadHeader = FileUploadUtils.getDownloadHeader(fileName);
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            // 将文件流写入到response的输出流中
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024]; // 1 KB 缓冲区
            int bytesRead;

            // 从 InputStream 读取数据并写入 ByteArrayOutputStream
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), downloadHeader, HttpStatus.OK);

            // if (!FileUploadUtils.isAllowDownload(fileName)) {
            // // 返回类型是ResponseEntity 不能捕获异常， 需要手动将错误填到 ResponseEntity
            // ResponseDTO<Object> fail = ResponseDTO.fail(
            // new ApiException(Business.COMMON_FILE_NOT_ALLOWED_TO_DOWNLOAD, fileName));
            // return new ResponseEntity<>(JacksonUtil.to(fail).getBytes(), null,
            // HttpStatus.OK);
            // }

            // String filePath =
            // FileUploadUtils.getFileAbsolutePath(UploadSubDir.DOWNLOAD_PATH, fileName);

            // HttpHeaders downloadHeader = FileUploadUtils.getDownloadHeader(fileName);

            // response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            // return new ResponseEntity<>(FileUtil.readBytes(filePath), downloadHeader,
            // HttpStatus.OK);
        } catch (Exception e) {
            log.error("下载文件失败", e);
            return null;
        }
    }

    /**
     * 通用上传请求（单个）
     */
    @Operation(summary = "单个上传文件")
    @PostMapping("/upload")
    public ResponseDTO<UploadDTO> uploadFile(MultipartFile file, @RequestParam(required = false) String type) {
        if (file == null) {
            throw new ApiException(ErrorCode.Business.UPLOAD_FILE_IS_EMPTY);
        }

        // 上传并返回新文件名称
        String fileName;
        System.out.println(type == "minio");
        if ("minio".equals(type)) {
            // String mimeType = file.getContentType();
            String mimeType = file.getContentType();
            ObjectWriteResponse objectWriteResponse = minioUtils.upload(UploadSubDir.UPLOAD_PATH, file,
                    minioConfig.getBucketName(), mimeType);
            fileName = objectWriteResponse.object();
            fileName = "/" + fileName;
        } else {
            fileName = FileUploadUtils.upload(UploadSubDir.UPLOAD_PATH, file);
        }
        String url = ServletHolderUtil.getContextUrl() + fileName;

        // 获取mimeType
        UploadDTO uploadDTO = UploadDTO.builder()
                // 全路径
                .url(url)
                // 相对路径
                .fileName(fileName)
                // 新生成的文件名
                .newFileName(FileNameUtil.getName(fileName))
                // 原始的文件名
                .originalFilename(file.getOriginalFilename())
                .build();

        return ResponseDTO.ok(uploadDTO);
    }

    /**
     * 通用上传请求（多个）
     */
    @Operation(summary = "多个上传文件")
    @PostMapping("/uploads")
    public ResponseDTO<List<UploadDTO>> uploadFiles(List<MultipartFile> files,
            @RequestParam(required = false) String type) {
        if (CollUtil.isEmpty(files)) {
            throw new ApiException(ErrorCode.Business.UPLOAD_FILE_IS_EMPTY);
        }

        List<UploadDTO> uploads = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file != null) {
                // 上传并返回新文件名称
                String fileName;
                if (type == "minio") {
                    String mimeType = file.getContentType();

                    ObjectWriteResponse objectWriteResponse = minioUtils.upload(UploadSubDir.UPLOAD_PATH, file,
                            minioConfig.getBucketName(), mimeType);
                    fileName = objectWriteResponse.object();
                    fileName = "/" + fileName;
                } else {
                    fileName = FileUploadUtils.upload(UploadSubDir.UPLOAD_PATH, file);
                }
                String url = ServletHolderUtil.getContextUrl() + fileName;
                UploadDTO uploadDTO = UploadDTO.builder()
                        .url(url)
                        .fileName(fileName)
                        .newFileName(FileNameUtil.getName(fileName))
                        .originalFilename(file.getOriginalFilename()).build();

                uploads.add(uploadDTO);

            }
        }
        return ResponseDTO.ok(uploads);
    }

    // 文件预览
    @Operation(summary = "文件预览")
    @GetMapping("/preview")
    public void preview(String fileName, HttpServletResponse response, HttpServletRequest request) throws Exception {
        // 获取请求的ip
        // 获取param参数
        return;
        // }
    }
}
