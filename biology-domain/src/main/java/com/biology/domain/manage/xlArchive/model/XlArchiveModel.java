package com.biology.domain.manage.xlArchive.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.personnel.db.PersonnelEntity;
import com.biology.domain.manage.personnel.model.PersonnelFactory;
import com.biology.domain.manage.shiJuan.model.ResultShiJuanFactory;
import com.biology.domain.manage.xlArchive.db.XlArchiveEntity;
import com.biology.domain.manage.xlArchive.command.AddXlArchiveCommand;
import com.biology.domain.manage.xlArchive.command.UpdateXlArchiveCommand;
import com.biology.domain.manage.xlArchive.db.XlArchiveEntity;
import com.biology.domain.manage.xlArchive.db.XlArchiveService;
import com.biology.domain.system.dept.db.SysDeptEntity;
import com.biology.domain.system.user.command.AddUserCommand;
import com.biology.domain.system.user.model.UserModel;
import com.biology.domain.system.user.model.UserModelFactory;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class XlArchiveModel extends XlArchiveEntity {
    private XlArchiveService xlArchiveService;

    private UserModelFactory userModelFactory;

    private PersonnelFactory personnelFactory;

    public XlArchiveModel(XlArchiveService xlArchiveService, UserModelFactory userModelFactory,
            PersonnelFactory personnelFactory) {
        this.xlArchiveService = xlArchiveService;
        this.userModelFactory = userModelFactory;
        this.personnelFactory = personnelFactory;
    }

    public XlArchiveModel(XlArchiveEntity entity, XlArchiveService xlArchiveService,
            UserModelFactory userModelFactory, PersonnelFactory personnelFactory) {

        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.xlArchiveService = xlArchiveService;
        this.userModelFactory = userModelFactory;
        this.personnelFactory = personnelFactory;
    }

    public void loadAddXlArchiveCommand(AddXlArchiveCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "xlArchiveId");
        }
    }

    public void loadUpdateXlArchiveCommand(UpdateXlArchiveCommand command) {
        if (command != null) {
            loadAddXlArchiveCommand(command);
        }
    }

    public void checkPersonnel() {
        QueryWrapper<XlArchiveEntity> queryWrapper = new QueryWrapper<XlArchiveEntity>();
        queryWrapper.eq("personnel_id", getPersonnelId());
        List<XlArchiveEntity> pEntity = this.selectList(queryWrapper);
        if (pEntity != null && pEntity.size() > 0) {
            throw new ApiException(ErrorCode.Business.PERSONNOT_SERVICE_UNAVAILABLE, "该人员已经创建");
        }
    }

    public void addUser() {

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        PersonnelEntity pEntity = personnelFactory.loadById(getPersonnelId());
        AddUserCommand command = new AddUserCommand();
        command.setJobCode(pEntity.getCode());
        command.setNickname(pEntity.getName());
        command.setUsername(today + pEntity.getName());
        command.setRoleId(new Long(7));
        command.setStatus(1);
        if (pEntity.getCode() != null) {
            command.setPassword("Cp@" + today + pEntity.getCode());
        } else {
            command.setPassword("Cp@" + today);
        }
        if (pEntity.getSex().equals("男")) {
            command.setSex(0);
        } else {
            command.setSex(1);
        }

        UserModel model = userModelFactory.create();
        model.loadAddUserCommand(command);
        model.checkUsernameIsUnique();
        model.checkPhoneNumberIsUnique();
        model.checkEmailIsUnique();
        model.checkFieldRelatedEntityExist();
        model.resetPassword(command.getPassword());

        model.insert();

        setUserId(model.getUserId());
    }

    public boolean insert() {
        checkPersonnel();
        addUser();
        return super.insert();
    }
}
