package com.biology.domain.manage.caiYangData.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CaiYangFunDTO {
    @JsonAlias("DeviceName")
    private String deviceName;

    @JsonAlias("Online")
    private Integer online;

    @JsonAlias("WorkTime")
    private Integer workTime;

    @JsonAlias("WorkStatus")
    private Integer workStatus;
}
