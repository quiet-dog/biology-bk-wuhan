package com.biology.domain.manage.craftarchive.command;

import lombok.Data;

import java.util.Date;

import com.biology.common.annotation.ExcelColumn;

@Data
public class ExcelCraftArchiveCommand {

    @ExcelColumn(name = "工艺档案编号")
    private String craftArchiveCode;

    @ExcelColumn(name = "工艺档案名称")
    private String craftArchiveName;

    @ExcelColumn(name = "版本")
    private String version;

    @ExcelColumn(name = "工艺制定人员")
    private String creator;

    @ExcelColumn(name = "创建日期")
    private Date createDate;

}
