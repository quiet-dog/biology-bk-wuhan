package com.biology.domain.manage.shiJuan.dto;

import java.util.List;

import lombok.Data;

@Data
public class CePingJieGuoTongJiEchart {
    private String name;
    private List<Integer> data;

    public CePingJieGuoTongJiEchart(String name, List<Integer> data) {
        this.name = name;
        this.data = data;
    }
}
