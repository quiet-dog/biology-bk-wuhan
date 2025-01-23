package com.biology.domain.manage.materials.db;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biology.domain.manage.materials.dto.MaterialsEasyDTO;
import com.biology.domain.manage.materials.dto.NormalDTO;

public interface MaterialsService extends IService<MaterialsEntity> {
    public MaterialsEntity getMaterialsByCode(String code);

    public NormalDTO getAllNormal();

    public List<MaterialsEasyDTO> getMaterialsEasy();

}
