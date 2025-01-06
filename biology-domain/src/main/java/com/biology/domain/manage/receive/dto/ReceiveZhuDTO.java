package com.biology.domain.manage.receive.dto;

import java.util.List;

import lombok.Data;

@Data
public class ReceiveZhuDTO {
    private List<String> xData;
    // 生产数量
    private List<Double> productionData;
    // 研发领用
    private List<Double> researchData;
    // 维修用量
    private List<Double> maintenanceData;
    // 实验用量
    private List<Double> experimentData;
    // 清洁用量
    private List<Double> cleanData;
    // 其他领用
    private List<Double> otherData;
}
