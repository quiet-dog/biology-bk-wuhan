package com.biology.domain.manage.xunJianHistory.dto;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.domain.manage.xunJianHistory.db.XunJianHistoryEntity;

import lombok.Data;

@Data
public class XunJianHistoryDTO {

    private String xunJianLeiXing;

    private Long xunJianId;

    private String status;

    private Long xunJianHistoryId;

    private List<Integer> timeRange;

    private List<Integer> dayRange;

    private String fanWei;

    private String xunJianPinLu;

    private Integer total;

    private Date createTime;

    public XunJianHistoryDTO(XunJianHistoryEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
    }

}
