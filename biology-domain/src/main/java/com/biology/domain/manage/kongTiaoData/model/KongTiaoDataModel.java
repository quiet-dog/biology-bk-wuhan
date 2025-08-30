package com.biology.domain.manage.kongTiaoData.model;

import com.biology.domain.manage.kongTiaoData.command.AddKongTiaoDataCommand;
import com.biology.domain.manage.kongTiaoData.command.UpdateKongTiaoDataCommand;
import com.biology.domain.manage.kongTiaoData.db.KongTiaoDataEntity;
import com.biology.domain.manage.kongTiaoData.db.KongTiaoDataService;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class KongTiaoDataModel extends KongTiaoDataEntity {
    private KongTiaoDataService kongTiaoDataService;

    public KongTiaoDataModel(KongTiaoDataService kongTiaoDataService) {
        this.kongTiaoDataService = kongTiaoDataService;
    }

    public KongTiaoDataModel(KongTiaoDataEntity entity, KongTiaoDataService kongTiaoDataService) {

        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.kongTiaoDataService = kongTiaoDataService;
    }

    public void loadAddKongTiaoDataCommand(AddKongTiaoDataCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "kongTiaoDataId");
        }
    }

    public void loadUpdateKongTiaoDataCommand(UpdateKongTiaoDataCommand command) {
        if (command != null) {
            loadAddKongTiaoDataCommand(command);
        }
    }

    public boolean insert() {
        super.insert();
        return true;
    }

}
