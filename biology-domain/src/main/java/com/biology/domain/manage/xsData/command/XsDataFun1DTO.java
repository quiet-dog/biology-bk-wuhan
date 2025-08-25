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

    @JsonAlias("WordTime")
    private Integer wordTime;

    @JsonAlias("WorkStatus")
    private Integer workStatus;

    @JsonAlias("HiginVoltageStatus")
    private Integer highVoltageStatus;
}
