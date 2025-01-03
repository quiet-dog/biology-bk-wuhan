package com.biology.domain.manage.craftdisposemanual.command;

import lombok.Data;

import com.biology.common.annotation.ExcelColumn;


@Data
public class ExcelDisposeManualCommand {

    @ExcelColumn(name = "工艺节点编号")
    private String nodeCode;

    @ExcelColumn(name = "发生问题")
    private String problemDescription;

    @ExcelColumn(name = "紧急处理流程")
    private String emergencyProcess;

    @ExcelColumn(name = "责任划分")
    private String responsibilityDivision;

    @ExcelColumn(name = "所需时间")
    private String requiredTime;

    @ExcelColumn(name = "预防措施")
    private String preventiveMeasures;

}
