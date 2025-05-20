package com.biology.domain.manage.materials.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class MhistoryDTO {
    private String type;
    private double num;
    private double stock;
    private String batch;
    private Date createTime;
}
