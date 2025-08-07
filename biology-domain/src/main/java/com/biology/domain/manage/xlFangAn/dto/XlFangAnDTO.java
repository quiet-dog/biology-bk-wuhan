package com.biology.domain.manage.xlFangAn.dto;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.biology.domain.manage.xlFangAn.db.XlFangAnEntity;

import lombok.Data;

@Data
public class XlFangAnDTO {

    private String name;

    private Long xlFangAnId;

    private List<String> shiJuanTypes;

    private List<Integer> userIds;

    public XlFangAnDTO(XlFangAnEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
    }
}
