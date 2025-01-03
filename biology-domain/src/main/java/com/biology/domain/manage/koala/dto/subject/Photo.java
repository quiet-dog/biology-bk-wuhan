package com.biology.domain.manage.koala.dto.subject;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Photo {
    private String url;
    @JsonProperty("subject_id")
    private Integer subjectId;
    @JsonProperty("company_id")
    private Integer companyId;
    private Integer id;
    private Integer version;
    private Integer quality;
    @JsonProperty("origin_url")
    private String originUrl;
    // private Integer gender;
    // private String avatar;
}
