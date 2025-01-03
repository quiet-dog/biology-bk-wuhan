package com.biology.domain.manage.sop.dto;

import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.sop.db.SopFileEntity;

import lombok.Data;

@Data
public class SopFileDTO {

    private Long sopId;

    private String path;

    public SopFileDTO(SopFileEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
    }
}
