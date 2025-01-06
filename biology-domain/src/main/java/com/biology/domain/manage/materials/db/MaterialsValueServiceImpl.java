package com.biology.domain.manage.materials.db;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biology.domain.manage.materials.dto.NormalDTO;

@Service
public class MaterialsValueServiceImpl extends ServiceImpl<MaterialsValueMapper, MaterialsValueEntity>
        implements MaterialsValueService {

    @Override
    public List<MaterialsValueEntity> selectByMaterialsId(Long materialsId) {
        QueryWrapper<MaterialsValueEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("materials_id", materialsId);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<String> selectTypes() {
        QueryWrapper<MaterialsValueEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("level").groupBy("level");
        return baseMapper.selectObjs(queryWrapper).stream()
                .filter(Objects::nonNull)
                .map(Object::toString)
                .collect(Collectors.toList());
    }

}
