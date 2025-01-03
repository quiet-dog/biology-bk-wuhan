package com.biology.domain.manage.gosip.dto.channels;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ChannaelListDTO {
    @JsonProperty("Total")
    private Integer total;
    @JsonProperty("List")
    private List<ChannelDTO> list;
}
