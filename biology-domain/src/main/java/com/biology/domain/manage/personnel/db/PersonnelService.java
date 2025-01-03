package com.biology.domain.manage.personnel.db;

import com.baomidou.mybatisplus.extension.service.IService;

public interface PersonnelService extends IService<PersonnelEntity> {
    public PersonnelEntity getPersonnelByCode(String code);
}
