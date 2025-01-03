package com.biology.domain.manage.event.dto;

import java.util.List;

import lombok.Data;

@Data
public class HistoryEventEchart {
    private List<String> time;

    private List<Integer> data;
}
