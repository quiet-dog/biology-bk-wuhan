package com.biology.domain.manage.materials.command;

import lombok.Data;

@Data
public class AddStockCommand {
    private Long materialsId;
    private double stock;
}
