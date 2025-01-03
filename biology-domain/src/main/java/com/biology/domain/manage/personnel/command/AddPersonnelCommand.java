package com.biology.domain.manage.personnel.command;

import java.util.Date;

import com.biology.common.annotation.ExcelColumn;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddPersonnelCommand {

    @Schema(description = "员工编号")
    @ExcelColumn(name = "员工编号")
    private String code;

    @Schema(description = "员工姓名")
    @ExcelColumn(name = "员工姓名")
    private String name;

    @Schema(description = "员工性别")
    @ExcelColumn(name = "员工性别")
    private String sex;
    // 部门
    @Schema(description = "部门")
    @ExcelColumn(name = "部门")
    private String department;

    @Schema(description = "身份证号")
    @ExcelColumn(name = "身份证号")
    private String card;

    @Schema(description = "邮箱")
    @ExcelColumn(name = "邮箱")
    private String email;

    // 岗位
    @Schema(description = "岗位")
    @ExcelColumn(name = "岗位")
    private String post;
    // 工号
    @Schema(description = "工号")
    @ExcelColumn(name = "工号")
    private String jobCode;
    // 职级
    @Schema(description = "职级")
    @ExcelColumn(name = "职级")
    private String pRank;
    // 学历
    @Schema(description = "学历")
    @ExcelColumn(name = "学历")
    private String education;
    // 联系方式
    @Schema(description = "联系方式")
    @ExcelColumn(name = "联系方式")
    private String contact;
    // 入职时间
    @Schema(description = "入职时间")
    @ExcelColumn(name = "入职时间")
    private Date entryTime;
    // 离职时间
    @Schema(description = "离职时间")
    @ExcelColumn(name = "离职时间")
    private Date leaveTime;

    @Schema(description = "外部ID")
    private Integer outId;
}
