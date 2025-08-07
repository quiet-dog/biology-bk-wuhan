package com.biology.domain.manage.shiJuan.dto;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.shiJuan.db.ResultShiJuanEntity;
import com.biology.domain.manage.shiJuan.db.Score;
import com.biology.domain.manage.xwDevice.db.XwDeviceEntity;

import lombok.Data;

@Data
public class ResultShiJuanDTO {

    private String type;

    private Double score;

    private Long xlShiJuanId;

    private Long xlFangAnId;

    private List<Score> result;

    private Long resultId;

    private Long lastTime;

    private Long userId;

    public ResultShiJuanDTO(ResultShiJuanEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
    }
}
