package com.biology.domain.manage.smThreshold.dto;

import java.util.List;

import com.biology.domain.manage.smThreshold.db.SmThresholdEntity;

import lombok.Data;

@Data
public class SmThresholdResDTO {
    public String type;

    public List<SmThresholdEntity> data;
}
