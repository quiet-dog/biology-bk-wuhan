package com.biology.domain.manage.gosip.dto.channels;

import lombok.Data;

@Data
public class ChannelQuery {
    private Integer limit;
    private Integer skip;
    private String sort;
    private String name;
}
