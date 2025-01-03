package com.biology.domain.manage.gosip.dto.streams;

import lombok.Data;

@Data
public class ChannelStreamQueryDTO {
    private String id;
    private Integer replay;
    private Integer page;
    private Integer end;
}
