package com.biology.domain.manage.materials.model;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.domain.manage.event.db.EventService;
import com.biology.domain.manage.materials.command.AddMaterialsCommand;
import com.biology.domain.manage.materials.command.ValueCommand;
import com.biology.domain.manage.materials.db.MaterialsEntity;
import com.biology.domain.manage.materials.db.MaterialsService;
import com.biology.domain.manage.materials.db.MaterialsValueEntity;
import com.biology.domain.manage.materials.db.MaterialsValueService;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class MaterialsModel extends MaterialsEntity {
    private MaterialsService materialsService;

    private MaterialsValueService materialsValueService;

    private EventService eventService;

    private List<ValueCommand> values;

    public MaterialsModel(MaterialsService materialsService, MaterialsValueService materialsValueService,
            EventService eventService) {
        this.materialsService = materialsService;
        this.materialsValueService = materialsValueService;
        this.eventService = eventService;
    }

    public MaterialsModel(MaterialsEntity entity, MaterialsService materialsService,
            MaterialsValueService materialsValueService, EventService eventService) {
        this(materialsService, materialsValueService, eventService);
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
    }

    public void loadAddMaterialsCommand(AddMaterialsCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "materialsId");
        }
    }

    public boolean cleanValues() {
        QueryWrapper<MaterialsValueEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("materials_id", getMaterialsId());
        materialsValueService.remove(queryWrapper);
        return true;
    }

    public boolean addValues() {
        if (values != null && !values.isEmpty()) {
            List<MaterialsValueEntity> entities = new ArrayList<>();
            for (ValueCommand value : values) {
                MaterialsValueEntity entity = new MaterialsValueEntity();
                entity.setMaterialsId(getMaterialsId());
                entity.setValue(value.getValue());
                entity.setSCondition(value.getSCondition());
                entity.setLevel(value.getLevel());
                entities.add(entity);
            }
            materialsValueService.saveBatch(entities);
        }
        return true;
    }

    // public boolean updateValues() {
    // // QueryWrapper<MaterialsValueEntity> queryWrapper = new QueryWrapper<>();
    // // queryWrapper.eq("materials_id", getMaterialsId());
    // // List<MaterialsValueEntity> entities =
    // // materialsValueService.list(queryWrapper);
    // // List<MaterialsValueEntity> newEntities = new ArrayList<>();
    // // List<Long> exitIds = new ArrayList<>();

    // // for (ValueCommand value : values) {
    // // if (value.getMaterialsValueId() != null && value.getMaterialsValueId() >
    // 0) {
    // // exitIds.add(value.getMaterialsValueId());
    // // }
    // // boolean find = false;
    // // for (MaterialsValueEntity entity : entities) {
    // // if (entity.getMaterialsValueId().equals(value.getMaterialsValueId())) {
    // // entity.setValue(value.getValue());
    // // entity.setSCondition(value.getSCondition());
    // // entity.setLevel(value.getLevel());
    // // newEntities.add(entity);
    // // find = true;
    // // break;
    // // }
    // // }
    // // if (!find) {
    // // MaterialsValueEntity entity = new MaterialsValueEntity();
    // // entity.setMaterialsId(getMaterialsId());
    // // entity.setValue(value.getValue());
    // // entity.setSCondition(value.getSCondition());
    // // entity.setLevel(value.getLevel());
    // // newEntities.add(entity);
    // // }

    // // }

    // // // 删除不存在的
    // // for (MaterialsValueEntity entity : entities) {
    // // if (!exitIds.contains(entity.getMaterialsValueId())) {
    // // materialsValueService.removeById(entity.getMaterialsValueId());
    // // }
    // // }

    // // return materialsValueService.saveOrUpdateBatch(newEntities);
    // }

    public boolean insert() {
        super.insert();
        return addValues();
    }

    public boolean updateById() {
        super.updateById();
        cleanValues();
        addValues();
        return true;
    }

    public boolean deleteById() {
        cleanValues();
        super.deleteById();
        return true;
    }

}
