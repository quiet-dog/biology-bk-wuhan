package com.biology.domain.manage.xunJianHistory.model;

import com.biology.domain.manage.xunJian.command.AddXunJianCommand;
import com.biology.domain.manage.xunJian.command.UpdateXunJianCommand;
import com.biology.domain.manage.xunJian.db.XunJianEntity;
import com.biology.domain.manage.xunJian.db.XunJianService;
import com.biology.domain.manage.xunJianHistory.command.AddXunJianHistoryCommand;
import com.biology.domain.manage.xunJianHistory.command.UpdateXunJianHistoryCommand;
import com.biology.domain.manage.xunJianHistory.db.XunJianHistoryEntity;
import com.biology.domain.manage.xunJianHistory.db.XunJianHistoryService;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class XunJianHistoryModel extends XunJianHistoryEntity {

    private XunJianHistoryService xunJianHistoryService;

    public XunJianHistoryModel(XunJianHistoryService xunJianHistoryService) {
        this.xunJianHistoryService = xunJianHistoryService;
    }

    public XunJianHistoryModel(XunJianHistoryEntity entity, XunJianHistoryService xunJianHistoryService) {
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.xunJianHistoryService = xunJianHistoryService;
    }

    public void loadAddXunJianHistoryCommand(AddXunJianHistoryCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "xunJianHistoryId");
        }
    }

    public void loadUpdateXunJianHistoryCommand(UpdateXunJianHistoryCommand command) {
        if (command != null) {
            loadAddXunJianHistoryCommand(command);
        }
    }

}
