package com.biology.domain.manage.gosip.dto.channels;

import com.biology.domain.manage.gosip.dto.streams.ChannelStreamsDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ChannaelListRootDTO {
    @JsonProperty("data")
    private ChannaelListDTO data;

    @JsonProperty("msgid")
    private String msgid;

    @JsonProperty("code")
    private String code;
}
