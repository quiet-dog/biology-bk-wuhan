package com.biology.domain.manage.craftprocess.model;

import com.biology.domain.manage.craftprocess.db.CraftProcessEntity;
import com.biology.domain.manage.craftprocess.db.CraftProcessService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CraftProcessModel {

    private final CraftProcessEntity entity;
    private final CraftProcessService craftProcessService;

    public CraftProcessModel(CraftProcessService craftProcessService) {
        this.entity = new CraftProcessEntity();
        this.craftProcessService = craftProcessService;
    }

    public void save() {
        craftProcessService.save(entity);
    }

    public void update() {
        craftProcessService.updateById(entity);
    }

    public void delete() {
        craftProcessService.removeById(entity.getCraftProcessId());
    }

    // 设置各个字段的方法
    public void setCraftArchiveId(Long craftArchiveId) {
        entity.setCraftArchiveId(craftArchiveId);
    }

    public void setCraftNodeId(Long craftNodeId) {
        entity.setCraftNodeId(craftNodeId);
    }

    public void setNodeOrder(Integer nodeOrder) {
        entity.setNodeOrder(nodeOrder);
    }

    public void setPersonnelFactors(String personnelFactors) {
        entity.setPersonnelFactors(personnelFactors);
    }

    public void setEquipmentFactors(String equipmentFactors) {
        entity.setEquipmentFactors(equipmentFactors);
    }

    public void setMaterialFactors(String materialFactors) {
        entity.setMaterialFactors(materialFactors);
    }

    public void setEnvironmentFactors(String environmentFactors) {
        entity.setEnvironmentFactors(environmentFactors);
    }
}
