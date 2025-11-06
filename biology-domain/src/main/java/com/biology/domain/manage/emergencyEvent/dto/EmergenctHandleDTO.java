package com.biology.domain.manage.emergencyEvent.dto;

import lombok.Data;

@Data
public class EmergenctHandleDTO {
    private String time;

    private Long handled;

    private Long unHandled;
}
