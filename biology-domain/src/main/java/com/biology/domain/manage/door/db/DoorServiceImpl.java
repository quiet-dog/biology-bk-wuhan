package com.biology.domain.manage.door.db;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biology.domain.manage.personnel.db.PersonnelMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoorServiceImpl extends ServiceImpl<DoorMapper, DoorEntity> implements DoorService {
    private final PersonnelMapper personnelMapper;

    public Double getDayStatus() {
        Integer allCount = personnelMapper.getAllCount();
        Integer dayStatus = baseMapper.getChuQinLvDay("通行");
        if (dayStatus == null) {
            return 0.0;
        }
        return (double) dayStatus / allCount;
    }

    public Double getChuQinLvDay(String enterStatus) {
        // Integer allCount = personnelMapper.getAllCount();
        // Integer dayStatus = baseMapper.getChuQinLvDay("通行");
        // if (dayStatus == null){
        // return 0.0,
        // }
        return 0.0;
    }
}
