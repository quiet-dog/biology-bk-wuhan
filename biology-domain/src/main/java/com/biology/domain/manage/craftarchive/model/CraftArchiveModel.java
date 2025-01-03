package com.biology.domain.manage.craftarchive.model;

import com.biology.domain.manage.craftarchive.command.AddCraftArchiveCommand;
import com.biology.domain.manage.craftarchive.command.UpdateCraftArchiveCommand;
import com.biology.domain.manage.craftarchive.db.CraftArchiveEntity;
import com.biology.domain.manage.craftarchive.db.CraftArchiveService;
import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CraftArchiveModel extends CraftArchiveEntity {

    private CraftArchiveService craftArchiveService;

    public CraftArchiveModel(CraftArchiveService craftArchiveService) {
        this.craftArchiveService = craftArchiveService;
    }

    public CraftArchiveModel(CraftArchiveEntity entity, CraftArchiveService craftArchiveService) {
        this(craftArchiveService);
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
    }

    public void loadAddCommand(AddCraftArchiveCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "craftArchiveId");
        }
    }

    public void loadUpdateCommand(UpdateCraftArchiveCommand command) {
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