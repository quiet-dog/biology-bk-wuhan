package com.biology.domain.manage.receive.command;

import com.biology.common.annotation.ExcelColumn;

import lombok.Data;

@Data
public class ExcelReceiveCommand {

    @ExcelColumn(name = "领用人编号")
    private String receiverCode;

    @ExcelColumn(name = "领用人工号")
    private String receiverEmployeeId;

    @ExcelColumn(name = "物料编号")
    private String materialCode;

    @ExcelColumn(name = "物料名称")
    private String materialName;

    @ExcelColumn(name = "领用数量")
    private double receiveNum;

    @ExcelColumn(name = "领用说明")
    private String receiveExplain;
}
