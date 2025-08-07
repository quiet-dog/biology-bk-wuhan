package com.biology.domain.manage.xlFangAn.model;

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.personnel.db.PersonnelEntity;
import com.biology.domain.manage.personnel.db.PersonnelService;
import com.biology.domain.manage.shiJuan.command.AddResultShiJuanCommand;
import com.biology.domain.manage.shiJuan.db.ResultShiJuanEntity;
import com.biology.domain.manage.shiJuan.db.ResultShiJuanService;
import com.biology.domain.manage.shiJuan.model.ResultShiJuanFactory;
import com.biology.domain.manage.shiJuan.model.ResultShiJuanModel;
import com.biology.domain.manage.xlFangAn.command.AddXlFangAnCommand;
import com.biology.domain.manage.xlFangAn.command.UpdateXlFangAnCommand;
import com.biology.domain.manage.xlFangAn.db.XlFangAnEntity;
import com.biology.domain.manage.xlFangAn.db.XlFangAnService;
import com.biology.domain.system.user.db.SysUserEntity;
import com.biology.domain.manage.xlFangAn.db.XlFangAnEntity;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class XlFangAnModel extends XlFangAnEntity {
    private XlFangAnService xlFangAnService;

    private ResultShiJuanFactory resultShiJuanFactory;

    public XlFangAnModel(XlFangAnService xlFangAnService, ResultShiJuanFactory resultShiJuanFactory) {
        this.xlFangAnService = xlFangAnService;
        this.resultShiJuanFactory = resultShiJuanFactory;
    }

    public XlFangAnModel(XlFangAnEntity entity, XlFangAnService xlFangAnService,
            ResultShiJuanFactory resultShiJuanFactory) {

        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.xlFangAnService = xlFangAnService;
        this.resultShiJuanFactory = resultShiJuanFactory;
    }

    public void loadAddXlFangAnCommand(AddXlFangAnCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "xlFangAnId");
        }
    }

    public void loadUpdateXlFangAnCommand(UpdateXlFangAnCommand command) {
        if (command != null) {
            loadAddXlFangAnCommand(command);
        }
    }

    // 检查是否存在还没有做的人员
    public void checkNotUser() {
        QueryWrapper<ResultShiJuanEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("user_id", getUserIds());
        List<ResultShiJuanEntity> list = new ResultShiJuanEntity().selectList(queryWrapper);
        if (!list.isEmpty()) {
            throw new ApiException(Business.PERSONNOT_SERVICE_UNAVAILABLE, "存在还没有未完成的人员，无法创建");
        }
    }

    public void addResultShiJuan() {
        getUserIds().forEach(item -> {
            getShiJuanTypes().forEach(type -> {
                AddResultShiJuanCommand aCommand = new AddResultShiJuanCommand();
                aCommand.setType(type);
                aCommand.setXlFangAnId(getXlFangAnId());
                aCommand.setUserId(new Long(item));
                ResultShiJuanModel rModel = resultShiJuanFactory.create();
                rModel.loadAddResultShiJuanCommand(aCommand);
                rModel.insert();
            });
        });
    }

    public boolean insert() {
        checkNotUser();
        if (super.insert()) {
            addResultShiJuan();
        }
        return true;
    }

}
