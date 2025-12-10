package com.biology.domain.manage.smData.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostRenTiMessageDTO {

    private PostRenTiDataDTO data;
    private String type;

}
