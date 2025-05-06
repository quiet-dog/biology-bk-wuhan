package com.biology.domain.manage.koala.dto.event;

import com.biology.domain.manage.koala.dto.subject.SubjectDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class KoalaEventDTO {
    private String confidence;
    private Integer eventType;
    private Long timestamp;
    private Integer gender;
    private Integer age;
    private String photo;
    private KoalaScreenDTO screen;
    @JsonProperty("verification_mode")
    private String verificationMode;

    @JsonProperty("pass_type")
    private Integer passType;

    private SubjectDTO subject;

    private Integer id;
}
