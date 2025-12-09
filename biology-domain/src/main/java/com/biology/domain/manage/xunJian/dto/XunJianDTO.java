package com.biology.domain.manage.xunJian.dto;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.xunJian.db.XunJianEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class XunJianDTO {

    private Long xunJianId;

    private String fanWei;

    private String xunJianPinLu;

    private String xunJianLeiXing;

    // private Long startTime;

    // private Long endTime;

    private Boolean enable;

    private List<Integer> timeRange;

    private List<Integer> dayRange;

    public XunJianDTO(XunJianEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
    }

}
