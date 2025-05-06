package com.biology.domain.manage.door.command;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddDoorCommand {

    @Schema(description = "人员档案Id")
    private Long personnelId;

    @Schema(description = "门禁编号")
    private String doorCode;

    @Schema(description = "进入状态")
    private String enterStatus;

    @Schema(description = "门禁设备编号")
    private String doorDeviceCode;

    @Schema(description = "门禁地点")
    private String doorPlace;

    @Schema(description = "进入状态")
    @TableField("clock_in")
    private Integer clockIn;

    @Schema(description = "离开状态")
    @TableField("clock_out")
    private Integer clockOut;

    @Schema(description = "门禁记录时间")
    @TableField("door_date")
    private Long doorDate;

    @Schema(description = "门禁人员部门")
    @TableField("department")
    private String department;

    @Schema(description = "门禁人员姓名")
    @TableField("name")
    private String name;

    @Schema(description = "门禁人员工作时间")
    @TableField("worktime")
    private String worktime;

    @Schema(description = "门禁人员描述")
    @TableField("description")
    private String description;

    @Schema(description = "门禁人员头像")
    @TableField("avatar")
    private String avatar;

    @Schema(description = "门禁人员工号")
    @TableField("job_number")
    private String jobNumber;

    @Schema(description = "门禁人员extraId")
    @TableField("extra_id")
    private String extraId;

    @Schema(description = "门禁人员Id")
    @TableField("out_id")
    private Long outId;

    @Schema(description = "门禁上班时间")
    @TableField("check_in_time")
    private Integer checkInTime;

    @Schema(description = "门禁下班时间")
    @TableField("check_out_time")
    private Integer checkOutTime;

    @TableField("event_type")
    private String eventType;

    @TableField("verification_mode")
    private String verificationMode;
}
