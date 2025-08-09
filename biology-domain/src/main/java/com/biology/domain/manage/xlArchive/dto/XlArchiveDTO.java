package com.biology.domain.manage.xlArchive.dto;

import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.personnel.db.PersonnelEntity;
import com.biology.domain.manage.xlArchive.db.XlArchiveEntity;
import com.biology.domain.system.user.db.SysUserEntity;

import lombok.Data;

@Data
public class XlArchiveDTO {
    private Long xlArchiveId;

    private Long personnelId;

    private Long userId;

    private String status;

    private String jobCode;

    private String nickname;

    private String username;

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
