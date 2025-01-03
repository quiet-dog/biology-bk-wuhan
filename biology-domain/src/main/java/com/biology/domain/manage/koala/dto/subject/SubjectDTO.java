package com.biology.domain.manage.koala.dto.subject;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SubjectDTO {
    @JsonProperty("subject_type")
    private Integer subjectType;

    @JsonProperty("extra_id")
    private String extraId;

    @JsonProperty("visit_notify")
    private Boolean visitNotify;

    @JsonProperty("create_time")
    private Long createTime;

    private Integer id;

    private String title;

    @JsonProperty("company_id")
    private Integer companyId;

    @JsonProperty("job_number")
    private String jobNumber;

    @JsonProperty("entry_date")
    private Integer entryDate;

    @JsonProperty("wg_number")
    private String wgNumber;

    private String department;

    private String email;

    @JsonProperty("end_time")
    private Integer endTime;

    @JsonProperty("password_reseted")
    private Boolean passwordReseted;

    private String description;

    private String pinyin;

    @JsonProperty("start_time")
    private Integer startTime;

    private String interviewee;

    private String phone;

    private String birthday;

    private Integer purpose;

    @JsonProperty("groups")
    private List<SubjectGroups> groups;

    @JsonProperty("come_from")
    private String comeFrom;

    private String remark;

    private String name;

    @JsonProperty("photos")
    private List<Photo> photos;

    private Integer gender;

    private String avatar;

    @JsonProperty("interviewee_pinyin")
    private String intervieweePinyin;
}
