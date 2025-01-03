package com.biology.domain.manage.alarmlevel.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.domain.manage.alarmlevel.command.AddAlarmlevelCommand;
import com.biology.domain.manage.alarmlevel.command.AlarmDetail;
import com.biology.domain.manage.alarmlevel.command.UpdateAlarmlevelCommand;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelDetailEntity;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelDetailService;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelEntity;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelService;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class AlarmlevelModel extends AlarmlevelEntity {

    private AlarmlevelService alarmlevelService;

    private AlarmlevelDetailService alarmlevelDetailService;

    private List<AlarmDetail> details;

    public AlarmlevelModel(AlarmlevelService alarmlevelService, AlarmlevelDetailService alarmlevelDetailService) {
        this.alarmlevelService = alarmlevelService;
        this.alarmlevelDetailService = alarmlevelDetailService;
    }

    public AlarmlevelModel(AlarmlevelEntity entity, AlarmlevelService alarmlevelService,
            AlarmlevelDetailService alarmlevelDetailService) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
        this.alarmlevelService = alarmlevelService;
        this.alarmlevelDetailService = alarmlevelDetailService;
    }

    public void loadAddCommand(AddAlarmlevelCommand command) {
        if (command != null) {
            BeanUtils.copyProperties(command, this, "alarmlevelId");
        }
    }

    public void loadUpdateCommand(UpdateAlarmlevelCommand command) {
        if (command != null) {
            BeanUtils.copyProperties(command, this);
        }
    }

    public void saveDetail() {
        if (details != null && !details.isEmpty()) {
            List<AlarmlevelDetailEntity> detailEntities = new ArrayList<>();
            for (AlarmDetail detail : details) {
                AlarmlevelDetailEntity detailEntity = new AlarmlevelDetailEntity();
                detailEntity.setLevel(detail.getLevel());
                detailEntity.setMax(detail.getMax());
                detailEntity.setMin(detail.getMin());
                detailEntity.setAlarmlevelId(getAlarmlevelId());
                detailEntities.add(detailEntity);
            }
            alarmlevelDetailService.saveBatch(detailEntities);
        }
    }

    public void cleanDetail() {
        QueryWrapper<AlarmlevelDetailEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("alarmlevel_id", getAlarmlevelId());
        alarmlevelDetailService.remove(queryWrapper);
    }

    public boolean insert() {
        super.insert();
        saveDetail();
        return true;
    }

    public boolean updateById() {
        cleanDetail();
        super.updateById();
        saveDetail();
        return true;
    }

}
