package com.biology.domain.manage.detection.dto;

import java.util.List;

import lombok.Data;

@Data
public class DareaResultDTO {
    private List<String> xData;
    private List<SeriesDTO> series;
    private String unitName;
}
