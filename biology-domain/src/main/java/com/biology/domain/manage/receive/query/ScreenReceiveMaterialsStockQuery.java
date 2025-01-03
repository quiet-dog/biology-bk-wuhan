package com.biology.domain.manage.receive.query;

import lombok.Data;

@Data
public class ScreenReceiveMaterialsStockQuery {
    private String startTime;

    private String endTime;

    private Long materialsId;
}
