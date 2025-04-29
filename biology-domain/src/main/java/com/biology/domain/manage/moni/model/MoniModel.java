package com.biology.domain.manage.moni.model;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.domain.manage.moni.command.AddMoniCommand;
import com.biology.domain.manage.moni.command.UpdateMoniCommand;
import com.biology.domain.manage.moni.db.MoniEntity;
import com.biology.domain.manage.moni.db.MoniService;
import com.biology.domain.manage.moni.db.MoniThresholdEntity;
import com.biology.domain.manage.moni.db.MoniThresholdService;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class MoniModel extends MoniEntity {

    private MoniService moniService;

    private MoniThresholdService moniThresholdService;

    private List<Long> thresholdIds;

    private List<Long> equipmentIds;

    public MoniModel(MoniService moniService, MoniThresholdService moniThresholdService) {
        this.moniService = moniService;
        this.moniThresholdService = moniThresholdService;
    }

    public MoniModel(MoniEntity entity, MoniService moniService, MoniThresholdService moniThresholdService) {
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.moniService = moniService;
        this.moniThresholdService = moniThresholdService;
    }

    public void loadAddMoniCommand(AddMoniCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "moniId");
        }
    }

    public void loadUpdateMoniCommand(UpdateMoniCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this);
        }
    }

    public void cleanThresholds() {
        if (getMoniId() != null) {
            QueryWrapper<MoniThresholdEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("moni_id", getMoniId());
            moniThresholdService.remove(queryWrapper);
        }
    }

    public void addThresholds() {
        if (getMoniId() != null && thresholdIds != null) {
            for (Long thresholdId : thresholdIds) {
                MoniThresholdEntity entity = new MoniThresholdEntity();
                entity.setMoniId(getMoniId());
                entity.setThresholdId(thresholdId);
                moniThresholdService.save(entity);
            }
        }
        if (getMoniId() != null && equipmentIds != null) {
            for (Long equipmentId : equipmentIds) {
                MoniThresholdEntity entity = new MoniThresholdEntity();
                entity.setMoniId(getMoniId());
                entity.setEquipmentId(equipmentId);
                moniThresholdService.save(entity);
            }
        }
    }

    public boolean insert() {
        super.insert();
        addThresholds();
        return true;
    }

    public boolean updateById() {
        super.updateById();
        cleanThresholds();
        addThresholds();
        return true;
    }

    public boolean deleteById() {
        cleanThresholds();
        return super.deleteById();
    }
}
