package com.biology.domain.manage.smData.model;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.personnel.db.PersonnelEntity;
import com.biology.domain.manage.personnel.db.PersonnelService;
import com.biology.domain.manage.smData.command.AddSmDataCommand;
import com.biology.domain.manage.smData.command.UpdateSmDataCommand;
import com.biology.domain.manage.smData.db.SmDataEntity;
import com.biology.domain.manage.smData.db.SmDataService;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SmDataModel extends SmDataEntity {
    private SmDataService smDataService;

    public SmDataModel(SmDataService smDataService) {
        this.smDataService = smDataService;
    }

    public SmDataModel(SmDataEntity entity, SmDataService smDataService) {

        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.smDataService = smDataService;
    }

    public void loadAddSmDataCommand(AddSmDataCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "smDataId");
        }
    }

    public void loadUpdateSmDataCommand(UpdateSmDataCommand command) {
        if (command != null) {
            loadAddSmDataCommand(command);
        }
    }

}
