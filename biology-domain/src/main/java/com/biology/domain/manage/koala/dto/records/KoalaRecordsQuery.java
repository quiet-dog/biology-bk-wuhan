package com.biology.domain.manage.koala.dto.records;

import org.springframework.util.MultiValueMap;

import lombok.Data;

@Data
public class KoalaRecordsQuery {
    private Integer startTime;
    private Integer endTime;
    private String userName;
    private String department;
    private Integer subjectId;
    private Integer page;
    private Integer size;

    // toMap
    public MultiValueMap<String, String> toMap() {
        MultiValueMap<String, String> map = new org.springframework.util.LinkedMultiValueMap<>();
        if (startTime != null) {
            map.add("start_time", String.valueOf(startTime));
        }
        if (endTime != null) {
            map.add("end_time", String.valueOf(endTime));
        }
        if (userName != null) {
            map.add("user_name", userName);
        }
        if (department != null) {
            map.add("department", department);
        }
        if (subjectId != null) {
            map.add("subject_id", String.valueOf(subjectId));
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
