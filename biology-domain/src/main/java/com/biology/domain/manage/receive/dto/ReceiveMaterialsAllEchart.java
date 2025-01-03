package com.biology.domain.manage.receive.dto;

import java.util.List;

import lombok.Data;

@Data
public class ReceiveMaterialsAllEchart {
    private String name;

    private List<ReceiveMaterialsStockDTO> datas;

}
