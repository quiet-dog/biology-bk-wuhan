package com.biology.domain.manage.personnel.model;

import com.biology.domain.manage.personnel.command.AddPersonnelCommand;
import com.biology.domain.manage.personnel.command.UpdatePersonnelCommand;
import com.biology.domain.manage.personnel.db.PersonnelCardService;
import com.biology.domain.manage.personnel.db.PersonnelCertificatesService;
import com.biology.domain.manage.personnel.db.PersonnelEntity;
import com.biology.domain.manage.personnel.db.PersonnelService;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class PersonnelModel extends PersonnelEntity {
    private PersonnelService personnelService;

    private PersonnelCardService personnelCardService;

    private PersonnelCertificatesService personnelCertificatesService;

    public PersonnelModel(PersonnelService personnelService, PersonnelCardService personnelCardService,
            PersonnelCertificatesService personnelCertificatesService) {
        this.personnelService = personnelService;
        this.personnelCardService = personnelCardService;
        this.personnelCertificatesService = personnelCertificatesService;
    }

    public PersonnelModel(PersonnelEntity entity, PersonnelService personnelService,
            PersonnelCardService personnelCardService, PersonnelCertificatesService personnelCertificatesService) {
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.personnelService = personnelService;
        this.personnelCardService = personnelCardService;
        this.personnelCertificatesService = personnelCertificatesService;
    }

    public void loadAddPersonnelCommand(AddPersonnelCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "personnelId");
        }
    }

    public void loadUpdatePersonnelCommand(UpdatePersonnelCommand command) {
        if (command != null) {
            loadAddPersonnelCommand(command);
        }
    }
}
