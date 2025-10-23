package com.biology.domain.manage.runTime.dto;

import lombok.Data;

@Data
public class RunTimeDTO {

    private Long runTimeId;

    private Long equipmentId;

    private Long environmentId;

    private Boolean isStop;
}
