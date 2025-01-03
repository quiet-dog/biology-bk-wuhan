package com.biology.domain.manage.receive.query;

import lombok.Data;

@Data
public class ReceiveMaterialsStockQuery {
    private String materialsName;

    private Long materialsId;

    private String dateType;
}
