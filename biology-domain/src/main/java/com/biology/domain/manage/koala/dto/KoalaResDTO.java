package com.biology.domain.manage.koala.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class KoalaResDTO<T> {
    private Integer code;
    @JsonProperty("data")
    private T data;
    private Integer timecost;
    private KoalaPageDTO page;
}
