package com.biology.domain.manage.craftnode.model;

import java.util.Collections;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.domain.manage.craftnode.command.AddCraftNodeCommand;
import com.biology.domain.manage.craftnode.command.UpdateCraftNodeCommand;
import com.biology.domain.manage.craftnode.db.CraftNodeEntity;
import com.biology.domain.manage.craftnode.db.CraftNodeService;
import com.biology.domain.manage.event.db.EventEntity;
import com.biology.domain.manage.craftnode.db.CraftNodeEquipmentService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CraftNodeModel extends CraftNodeEntity {

    private CraftNodeService craftNodeService;
    private CraftNodeEquipmentService craftNodeEquipmentService;

    public CraftNodeModel(CraftNodeService craftNodeService, CraftNodeEquipmentService craftNodeEquipmentService) {
        this.craftNodeService = craftNodeService;
        this.craftNodeEquipmentService = craftNodeEquipmentService;
    }

    public CraftNodeModel(CraftNodeEntity entity, CraftNodeService craftNodeService,
            CraftNodeEquipmentService craftNodeEquipmentService) {
        this(craftNodeService, craftNodeEquipmentService);
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
    }

    public void loadAddCommand(AddCraftNodeCommand command) {
        if (command != null) {
            // if (command.getEquipmentIds() != null) {
            // this.setEquipmentIds(command.getEquipmentIds());
            // }
            BeanUtil.copyProperties(command, this, "nodeId");
        }
    }

    public void loadUpdateCommand(UpdateCraftNodeCommand command) {
        if (command != null) {
            // if (command.getEquipmentIds() != null) {
            // this.setEquipmentIds(command.getEquipmentIds());
            // }
            BeanUtil.copyProperties(command, this);
        }
    }

    @Override
    public boolean insert() {
        boolean result = super.insert();
        if (CollUtil.isNotEmpty(this.getEquipmentIds())) {
            craftNodeEquipmentService.saveCraftNodeEquipments(this.getCraftNodeId(), this.getEquipmentIds());
        }
        return result;
    }

    // @Override
    public boolean updateById() {
        boolean result = super.updateById();
        if (CollUtil.isNotEmpty(this.getEquipmentIds())) {
            craftNodeEquipmentService.saveCraftNodeEquipments(this.getCraftNodeId(), this.getEquipmentIds());
        }
        return result;
    }

    @Override
    public boolean deleteById() {
        craftNodeEquipmentService.saveCraftNodeEquipments(this.getCraftNodeId(), Collections.emptyList());
        removeEvent();
        return super.deleteById();
    }

    public void removeEvent() {
        QueryWrapper<EventEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("craft_node_id", this.getCraftNodeId());
        List<EventEntity> eventEntities = new EventEntity().selectList(queryWrapper);
        for (EventEntity eventEntity : eventEntities) {
            eventEntity.deleteById();
        }
    }
}
