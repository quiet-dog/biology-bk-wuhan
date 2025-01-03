package com.biology.domain.manage.personnel.db;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
public class PersonnelServiceImpl extends ServiceImpl<PersonnelMapper, PersonnelEntity> implements PersonnelService {

    @Override
    public PersonnelEntity getPersonnelByCode(String code) {
        return baseMapper.getPersonnelByCode(code);
    }

}
