package com.biology.domain.manage.event.dto;

import java.util.List;

import lombok.Data;

@Data
public class EnvironmentStockEchart {
    private List<String> unitNames;
    private List<Integer> datas;
}
