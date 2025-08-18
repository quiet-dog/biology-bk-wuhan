package com.biology.domain.manage.smThreshold.command;

import java.util.List;

import lombok.Data;

@Data
public class AddSmThresholdAllReq {
    private Long id;

    private List<AddSmThresholdAll> data;
}
