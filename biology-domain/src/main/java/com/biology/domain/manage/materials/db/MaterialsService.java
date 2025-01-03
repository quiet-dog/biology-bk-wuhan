package com.biology.domain.manage.materials.db;

import com.baomidou.mybatisplus.extension.service.IService;

public interface MaterialsService extends IService<MaterialsEntity> {
    public MaterialsEntity getMaterialsByCode(String code);
}
