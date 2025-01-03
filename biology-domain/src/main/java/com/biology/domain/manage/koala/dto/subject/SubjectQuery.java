package com.biology.domain.manage.koala.dto.subject;

import org.springframework.util.MultiValueMap;

import lombok.Data;

@Data
public class SubjectQuery {
    // employee员工, visitor访客, yellowlist黄名单
    private String category;
    private String name;
    private String department;
    private String interviewee;
    private Integer startTime;
    private Integer endTime;
    private Integer screenId;
    private Boolean isBind;
    private String filterstr;
    private String remark;
    private String extraId;
    private Integer page;
    private Integer size;

    // toMap
    public MultiValueMap<String, String> toMap() {
        MultiValueMap<String, String> map = new org.springframework.util.LinkedMultiValueMap<>();
        if (category != null) {
            map.add("category", category);
        }
        if (name != null) {
            map.add("name", name);
        }
        if (department != null) {
            map.add("department", department);
        }
        if (interviewee != null) {
            map.add("interviewee", interviewee);
        }
        if (startTime != null) {
            map.add("start_time", String.valueOf(startTime));
        }
        if (endTime != null) {
            map.add("end_time", String.valueOf(endTime));
        }
        if (screenId != null) {
            map.add("screen_id", String.valueOf(screenId));
        }
        if (isBind != null) {
            map.add("is_bind", String.valueOf(isBind));
        }
        if (filterstr != null) {
            map.add("filterstr", filterstr);
        }
        if (remark != null) {
            map.add("remark", remark);
        }
        if (extraId != null) {
            map.add("extra_id", extraId);
        }
        if (page != null) {
            map.add("page", String.valueOf(page));
        }
        if (size != null) {
            map.add("size", String.valueOf(size));
        }
        return map;
    }
}
