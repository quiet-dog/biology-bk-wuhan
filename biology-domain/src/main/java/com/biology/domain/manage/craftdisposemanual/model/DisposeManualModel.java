package com.biology.domain.manage.craftdisposemanual.model;

import com.biology.domain.manage.craftdisposemanual.command.AddDisposeManualCommand;
import com.biology.domain.manage.craftdisposemanual.command.UpdateDisposeManualCommand;
import com.biology.domain.manage.craftdisposemanual.db.DisposeManualEntity;
import com.biology.domain.manage.craftdisposemanual.db.DisposeManualService;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class DisposeManualModel extends DisposeManualEntity {

    private DisposeManualService disposeManualService;

    public DisposeManualModel(DisposeManualService disposeManualService) {
        this.disposeManualService = disposeManualService;
    }

    public DisposeManualModel(DisposeManualEntity entity, DisposeManualService disposeManualService) {
        this(disposeManualService);
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
    }

    public void loadAddCommand(AddDisposeManualCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "disposeManualId");
        }
    }

    public void loadUpdateCommand(UpdateDisposeManualCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this);
        }
    }

    public boolean insert() {
        return super.insert();
    }

    public boolean update() {
        return super.updateById();
    }

    public boolean deleteById() {
        return super.deleteById();
    }
}
