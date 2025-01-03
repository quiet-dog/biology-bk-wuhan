package com.biology.domain.manage.materials.db;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class MaterialsServiceImpl extends ServiceImpl<MaterialsMapper, MaterialsEntity> implements MaterialsService {

    public MaterialsEntity getMaterialsByCode(String code) {
        return baseMapper.getMaterialsByCode(code);
    }

}
