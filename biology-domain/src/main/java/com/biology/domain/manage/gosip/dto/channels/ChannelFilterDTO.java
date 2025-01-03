package com.biology.domain.manage.gosip.dto.channels;

import lombok.Data;

@Data
public class ChannelFilterDTO {
    private String field_name;
    private String opertator;
    private String value;
}
