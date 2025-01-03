package com.biology.domain.manage.equipment.dto;

import java.util.List;

import lombok.Data;

@Data
public class EquipmentDataStockEchartDTO {
    private List<String> time;

    private List<Double> data;
}
