package com.biology.domain.manage.receive.dto;

import java.util.List;

import lombok.Data;

@Data
public class ReceiveStockEchart {
    private List<String> time;
    private List<Double> data;
}
