package com.biology.domain.manage.xlArchive.dto;

import org.springframework.beans.BeanUtils;

import com.biology.common.annotation.ExcelColumn;
import com.biology.common.annotation.ExcelSheet;
import com.biology.domain.manage.personnel.db.PersonnelEntity;
import com.biology.domain.manage.xlArchive.db.XlArchiveEntity;
import com.biology.domain.system.user.db.SysUserEntity;

import lombok.Data;

@ExcelSheet(name = "心理健康档案列表")
@Data
public class XlArchiveDTO {

    private Long xlArchiveId;

    private Long personnelId;

    private Long userId;

    @ExcelColumn(name = "状态")
    private String status;

    @ExcelColumn(name = "人员工号")
    private String jobCode;

    @ExcelColumn(name = "人员姓名")
    private String nickname;

    @ExcelColumn(name = "账号")
    private String username;

    @ExcelColumn(name = "所属部门")
    private String deptName;

    public XlArchiveDTO(XlArchiveEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
            addPersonnel();
        }
    }

    public void addPersonnel() {
        if (getPersonnelId() != null && getPersonnelId() > 0) {
            PersonnelEntity pEntity = new PersonnelEntity().selectById(getPersonnelId());
            if (pEntity != null) {
                setDeptName(pEntity.getDepartment());
                setNickname(pEntity.getName());
                setJobCode(pEntity.getCode());
            }

        }
        if (getUserId() != null && getUserId() > 0) {
            SysUserEntity sEntity = new SysUserEntity().selectById(getUserId());
            if (sEntity != null) {
                setUsername(sEntity.getUsername());
            }
        }
    }
}
