package com.biology.domain.manage.chuqin.model;

import com.biology.domain.manage.chuqin.command.AddChuQinCommand;
import com.biology.domain.manage.chuqin.db.ChuQinEntity;
import com.biology.domain.manage.chuqin.db.ChuQinService;
import com.biology.domain.manage.personnel.db.PersonnelService;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ChuQinModel extends ChuQinEntity {

    private ChuQinService chuQinService;

    private PersonnelService personnelService;

    public ChuQinModel(ChuQinService chuQinService, PersonnelService personnelService) {
        this.chuQinService = chuQinService;
        this.personnelService = personnelService;
    }

    public ChuQinModel(ChuQinEntity entity, ChuQinService chuQinService, PersonnelService personnelService) {
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.chuQinService = chuQinService;
        this.personnelService = personnelService;
    }

    public void loadAddChuQinCommand(AddChuQinCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "chuQinId");
        }
    }

    public void loadUpdateChuQinCommand(AddChuQinCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this);
        }
    }
}
