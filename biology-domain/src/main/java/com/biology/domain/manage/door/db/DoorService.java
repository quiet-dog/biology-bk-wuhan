package com.biology.domain.manage.door.db;

import com.baomidou.mybatisplus.extension.service.IService;

public interface DoorService extends IService<DoorEntity> {
    public Double getDayStatus();

    public Double getChuQinLvDay(String enterStatus);
}
