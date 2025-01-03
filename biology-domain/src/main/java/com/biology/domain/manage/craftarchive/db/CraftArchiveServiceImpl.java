package com.biology.domain.manage.craftarchive.db;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service
public class CraftArchiveServiceImpl extends ServiceImpl<CraftArchiveMapper, CraftArchiveEntity>
        implements CraftArchiveService {

    @Override
    public CraftArchiveEntity getById(Long craftArchiveId) {
        return super.getById(craftArchiveId);
    }

    @Override
    public void importCraftArchive(MultipartFile file) {
    }

}
