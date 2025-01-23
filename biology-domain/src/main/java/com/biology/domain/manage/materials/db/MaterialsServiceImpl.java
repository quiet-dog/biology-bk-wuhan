package com.biology.domain.manage.materials.db;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biology.domain.manage.materials.dto.MaterialsEasyDTO;
import com.biology.domain.manage.materials.dto.NormalDTO;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MaterialsServiceImpl extends ServiceImpl<MaterialsMapper, MaterialsEntity> implements MaterialsService {

    private final MaterialsValueService materialsValueService;

    public MaterialsEntity getMaterialsByCode(String code) {
        return baseMapper.getMaterialsByCode(code);
    }

    public NormalDTO getAllNormal() {
        NormalDTO result = new NormalDTO();
        result.setAbnormal(0);
        result.setNormal(0);
        List<MaterialsEntity> materialsEntities = baseMapper.selectList(null);
        for (MaterialsEntity materialsEntity : materialsEntities) {
            QueryWrapper<MaterialsValueEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("materials_id", materialsEntity.getMaterialsId());
            List<MaterialsValueEntity> materialsValueEntities = materialsValueService.list(queryWrapper);
            boolean flag = false;
            if (materialsValueEntities != null) {
                for (MaterialsValueEntity materialsValueEntity : materialsValueEntities) {
                    if (materialsValueEntity.getSCondition().equals("大于")
                            && materialsEntity.getStock() > materialsValueEntity.getValue()) {
                        result.setAbnormal(result.getAbnormal() + 1);
                        flag = true;
                        break;
                    }
                    if (materialsValueEntity.getSCondition().equals("小于")
                            && materialsEntity.getStock() < materialsValueEntity.getValue()) {
                        result.setAbnormal(result.getAbnormal() + 1);
                        flag = true;
                        break;
                    }
                    if (materialsValueEntity.getSCondition().equals("等于")
                            && materialsEntity.getStock() == materialsValueEntity.getValue()) {
                        result.setAbnormal(result.getAbnormal() + 1);
                        flag = true;
                        break;
                    }
                    if (materialsValueEntity.getSCondition().equals("大于等于")
                            && materialsEntity.getStock() >= materialsValueEntity.getValue()) {
                        result.setAbnormal(result.getAbnormal() + 1);
                        flag = true;
                        break;
                    }
                    if (materialsValueEntity.getSCondition().equals("小于等于")
                            && materialsEntity.getStock() <= materialsValueEntity.getValue()) {
                        result.setAbnormal(result.getAbnormal() + 1);
                        flag = true;
                        break;
                    }
                }
            }

            if (!flag) {
                result.setNormal(result.getNormal() + 1);
            }
        }
        return result;
    }

    public List<MaterialsEasyDTO> getMaterialsEasy() {
        List<MaterialsEasyDTO> result = new ArrayList<>();
        List<MaterialsEntity> materialsEntities = baseMapper.selectList(null);
        for (MaterialsEntity materialsEntity : materialsEntities) {
            result.add(new MaterialsEasyDTO(materialsEntity));
        }
        return result;
    }

}
