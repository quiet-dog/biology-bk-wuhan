package com.biology.domain.manage.xlFangAn.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.biology.common.annotation.ExcelColumn;
import com.biology.common.annotation.ExcelSheet;
import com.biology.domain.manage.xlFangAn.db.XlFangAnEntity;

import lombok.Data;

@ExcelSheet(name = "心理测评方案列表")
@Data
public class XlFangAnDTO {

    @ExcelColumn(name = "测评编号")
    private Long xlFangAnId;

    @ExcelColumn(name = "方案名称")
    private String name;

    private String types;

    private List<String> shiJuanTypes;

    private List<Integer> userIds;

    @ExcelColumn(name = "测评内容")
    private String shiJuanTypesStr;

    private Long pingGuTime;

    @ExcelColumn(name = "预计评估时间")
    private String pingGuTimeStr;

    public XlFangAnDTO(XlFangAnEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
            addShiJuanTypesStr();
        }
    }

    public void addShiJuanTypesStr() {
        if (getShiJuanTypes() != null && getShiJuanTypes().size() > 0) {
            setShiJuanTypesStr(String.join(",", getShiJuanTypes()));
        }

        if (getPingGuTime() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formatted = sdf.format(new Date(getPingGuTime()));
            setPingGuTimeStr(formatted);
        }
    }
}
