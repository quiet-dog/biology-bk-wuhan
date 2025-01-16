package com.biology.domain.manage.materials.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.materials.db.WarehouseEntity;

import lombok.Data;

@Data
public class WarehouseDTO {
    private Long materialsId;

    private Long warehouseId;

    private double stock;

    private String batch;

    private double remainStock;

    private Date createTime;

    public WarehouseDTO(WarehouseEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
    }
}
