package com.biology.domain.manage.alarm.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.biology.domain.manage.alarm.db.AlarmEntity;
import com.biology.domain.manage.materials.db.MaterialsEntity;
import com.biology.domain.manage.materials.dto.MaterialsDTO;

import lombok.Data;

@Data
public class AlarmDTO {
    private Long alarmId;

    private Long materialsId;

    private MaterialsDTO materials;

    private Date createTime;

    private double stock;

    public AlarmDTO(AlarmEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);

            if (entity.getMaterialsId() != null) {
                MaterialsEntity materialsEntity = new MaterialsEntity();
                setMaterials(new MaterialsDTO(materialsEntity.selectById(entity.getMaterialsId())));
            }
        }
    }
}
