package com.biology.domain.manage.detection.query;

import java.util.List;

import lombok.Data;

@Data
public class AllAreaDTO {
    private String unitName;
    private String beginTime;
    private String endTime;
    private List<String> areas;
}
