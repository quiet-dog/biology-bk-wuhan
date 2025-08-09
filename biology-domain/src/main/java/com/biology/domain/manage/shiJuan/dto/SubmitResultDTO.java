package com.biology.domain.manage.shiJuan.dto;

import java.util.List;

import com.biology.domain.manage.shiJuan.db.Score;

import lombok.Data;

@Data
public class SubmitResultDTO {
    private Long resultId;

    private List<Score> result;

    private Long useTime;
}
