package com.biology.domain.manage.task.dto;

import java.util.List;

import lombok.Data;

@Data
public class ShengOnelineResDTO {
    private Long code;
    private String message;
    private List<SmOnlineDataDTO> data;
}
