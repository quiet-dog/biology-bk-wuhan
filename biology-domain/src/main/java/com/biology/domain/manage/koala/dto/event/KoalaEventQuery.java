package com.biology.domain.manage.koala.dto.event;

import org.springframework.util.MultiValueMap;

import lombok.Data;

@Data
public class KoalaEventQuery {
    private String category;
    private Integer start;
    private Integer end;
    private Integer page;
    private Integer size;
    private Integer passType;

    public MultiValueMap<String, String> toMap() {
        MultiValueMap<String, String> map = new org.springframework.util.LinkedMultiValueMap<>();
        if (category != null) {
            map.add("category", category);
        }
        if (start != null) {
            map.add("start", String.valueOf(start));
        }
        if (end != null) {
            map.add("end", String.valueOf(end));
        }
        if (page != null) {
            map.add("page", String.valueOf(page));
        }
        if (size != null) {
            map.add("size", String.valueOf(size));
        }
        if (passType != null) {
            map.add("pass_type", String.valueOf(passType));
        }
        return map;
    }
}
