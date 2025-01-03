package com.biology.domain.manage.event.dto;

import java.util.List;

import com.biology.domain.manage.materials.db.MaterialsValueEntity;
import com.biology.domain.manage.threshold.db.ThresholdEntity;
import com.biology.domain.manage.threshold.db.ThresholdValueEntity;

import lombok.Data;

@Data
public class TypeDTO {
    private List<String> thresholdTypes;

    private List<String> materialsTypes;

    // public TypeDTO(ThresholdValueEntity thresholdTypes) {
    // this.thresholdTypes = thresholdTypes;
    // }

    // public TypeDTO(MaterialsValueEntity materialsTypes) {
    // this.materialsTypes = materialsTypes;
    // }
}
