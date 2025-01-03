package com.biology.domain.manage.emergency.dto;

import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.emergency.db.KeyWordEntity;

import lombok.Data;

@Data
public class KeywordDTO {
    private Long keywordId;

    private String keyword;

    public KeywordDTO(KeyWordEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
    }

}
