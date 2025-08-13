package com.biology.domain.manage.xlFangAn.dto;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.biology.common.annotation.ExcelColumn;
import com.biology.common.annotation.ExcelSheet;
import com.biology.domain.manage.xlFangAn.db.XlFangAnEntity;

import lombok.Data;

@ExcelSheet(name = "心理测评方案列表")
@Data
public class XlFangAnDTO {

    @ExcelColumn(name = "方案名称")
    private String name;

    @ExcelColumn(name = "测评编号")
    private Long xlFangAnId;

    @ExcelColumn(name = "测评编号")
    private String types;

    private List<String> shiJuanTypes;

    private List<Integer> userIds;

    public XlFangAnDTO(XlFangAnEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
    }
}
