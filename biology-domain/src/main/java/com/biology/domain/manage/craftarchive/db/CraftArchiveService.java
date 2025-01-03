package com.biology.domain.manage.craftarchive.db;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.service.IService;

@Service
public interface CraftArchiveService extends IService<CraftArchiveEntity> {

    CraftArchiveEntity getById(Long craftArchiveId);

    void importCraftArchive(MultipartFile file);
}
