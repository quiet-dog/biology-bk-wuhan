package com.biology.domain.manage.xunJian.model;

import com.biology.domain.manage.xunJian.command.AddXunJianCommand;
import com.biology.domain.manage.xunJian.command.UpdateXunJianCommand;
import com.biology.domain.manage.xunJian.db.XunJianEntity;
import com.biology.domain.manage.xunJian.db.XunJianService;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class XunJianModel extends XunJianEntity {

    private XunJianService xunJianService;

    public XunJianModel(XunJianService xunJianService) {
        this.xunJianService = xunJianService;
    }

    public XunJianModel(XunJianEntity entity, XunJianService xunJianService) {
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.xunJianService = xunJianService;
    }

    public void loadAddXunJianCommand(AddXunJianCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "xunJianId");
        }
    }

    public void loadUpdateXunJianCommand(UpdateXunJianCommand command) {
        if (command != null) {
            loadAddXunJianCommand(command);
        }
    }

}
