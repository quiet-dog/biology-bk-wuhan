package com.biology.domain.manage.caiYangData.model;

import com.biology.domain.manage.caiYangData.db.CaiYangDataEntity;
import com.biology.domain.manage.caiYangData.command.AddCaiYangDataCommand;
import com.biology.domain.manage.caiYangData.command.UpdateCaiYangDataCommand;
import com.biology.domain.manage.caiYangData.db.CaiYangDataEntity;
import com.biology.domain.manage.caiYangData.db.CaiYangDataService;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CaiYangDataModel extends CaiYangDataEntity {
    private CaiYangDataService caiYangDataService;

    public CaiYangDataModel(CaiYangDataService caiYangDataService) {
        this.caiYangDataService = caiYangDataService;
    }

    public CaiYangDataModel(CaiYangDataEntity entity, CaiYangDataService caiYangDataService) {

        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.caiYangDataService = caiYangDataService;
    }

    public void loadAddCaiYangDataCommand(AddCaiYangDataCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "caiYangDataId");
        }
    }

    public void loadUpdateCaiYangDataCommand(UpdateCaiYangDataCommand command) {
        if (command != null) {
            loadAddCaiYangDataCommand(command);
        }
    }
}
