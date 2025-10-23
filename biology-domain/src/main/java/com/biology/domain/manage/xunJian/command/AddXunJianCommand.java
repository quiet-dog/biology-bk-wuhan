package com.biology.domain.manage.xunJian.command;

import java.util.List;

import lombok.Data;

@Data
public class AddXunJianCommand {

    private String fanWei;

    private String xunJianPinLu;

    private String xunJianLeiXing;

    private Long startTime;

    private Long endTime;

    private Boolean enable;

    private List<Integer> timeRange;

}
