package com.biology.domain.manage.materials.db;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biology.domain.manage.materials.dto.NormalDTO;

public interface MaterialsValueService extends IService<MaterialsValueEntity> {

    public List<MaterialsValueEntity> selectByMaterialsId(Long materialsId);

    public List<String> selectTypes();

}
