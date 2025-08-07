package com.biology.domain.manage.shiJuan.model;

import com.biology.domain.manage.shiJuan.command.AddResultShiJuanCommand;
import com.biology.domain.manage.shiJuan.command.UpdateResultShiJuanCommand;
import com.biology.domain.manage.shiJuan.db.ResultShiJuanEntity;
import com.biology.domain.manage.shiJuan.db.ResultShiJuanService;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ResultShiJuanModel extends ResultShiJuanEntity {
    private ResultShiJuanService resultShiJuanService;

    public ResultShiJuanModel(ResultShiJuanService resultShiJuanService) {
        this.resultShiJuanService = resultShiJuanService;
    }

    public ResultShiJuanModel(ResultShiJuanEntity entity, ResultShiJuanService resultShiJuanService) {

        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.resultShiJuanService = resultShiJuanService;
    }

    public void loadAddResultShiJuanCommand(AddResultShiJuanCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "resultShiJuanId");
        }
    }

    public void loadUpdateResultShiJuanCommand(UpdateResultShiJuanCommand command) {
        if (command != null) {
            loadAddResultShiJuanCommand(command);
        }
    }
}
