package com.biology.domain.manage.smThreshold.command;

import java.util.List;

import lombok.Data;

@Data
public class AddSmThresholdAll {
    private String type;

    private List<AddSmThresholdCommand> values;
}
