package com.biology.domain.manage.xwAlarm.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class XingWeiCacheDTO extends XingWeiDTO {

    private Date createTime;

    public XingWeiCacheDTO(XingWeiDTO xingWeiDTO) {
        if (xingWeiDTO != null) {
            BeanUtils.copyProperties(xingWeiDTO, this);
        }
    }
}
