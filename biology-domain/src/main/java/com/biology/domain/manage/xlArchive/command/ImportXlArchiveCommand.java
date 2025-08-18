package com.biology.domain.manage.xlArchive.command;

import com.biology.common.annotation.ExcelColumn;

import lombok.Data;

@Data
public class ImportXlArchiveCommand {
    @ExcelColumn(name = "员工工号")
    private String jobCode;

    @ExcelColumn(name = "人员姓名")
    private String username;

    @ExcelColumn(name = "所属部门")
    private String deptName;
}
