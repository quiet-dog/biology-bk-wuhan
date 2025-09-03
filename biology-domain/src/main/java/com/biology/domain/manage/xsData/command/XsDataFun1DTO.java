package com.biology.domain.manage.xsData.command;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class XsDataFun1DTO {
    @JsonAlias("DeviceName")
    private String deviceName;

    @JsonAlias("Online")
    private Integer online;

    @JsonAlias("WorkTime")
    private Integer workTime;

    @JsonAlias("WorkStatus")
    private Integer workStatus;

    @JsonAlias("HighVoltageStatus")
    private Integer highVoltageStatus;
}
