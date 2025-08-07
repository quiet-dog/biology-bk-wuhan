package com.biology.domain.manage.shiJuan.model;

import com.biology.domain.manage.shiJuan.db.ShiJuanEntity;
import com.biology.domain.manage.shiJuan.command.AddShiJuanCommand;
import com.biology.domain.manage.shiJuan.command.UpdateShiJuanCommand;
import com.biology.domain.manage.shiJuan.db.ShiJuanService;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ShiJuanModel extends ShiJuanEntity {
    private ShiJuanService shiJuanService;

    public ShiJuanModel(ShiJuanService shiJuanService) {
        this.shiJuanService = shiJuanService;
    }

    public ShiJuanModel(ShiJuanEntity entity, ShiJuanService shiJuanService) {

        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.shiJuanService = shiJuanService;
    }

    public void loadAddShiJuanCommand(AddShiJuanCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "shiJuanId");
        }
    }

    public void loadUpdateShiJuanCommand(UpdateShiJuanCommand command) {
        if (command != null) {
            loadAddShiJuanCommand(command);
        }
    }
}
