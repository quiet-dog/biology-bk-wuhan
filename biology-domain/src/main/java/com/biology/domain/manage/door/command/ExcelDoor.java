package com.biology.domain.manage.door.command;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.annotation.ExcelColumn;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.door.db.DoorEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ExcelDoor {

    @Schema(description = "姓名")
    @ExcelColumn(name = "姓名")
    @NotBlank(message = "人员档案名称不能为空")
    private String name;

    // @Schema(description = "部门")
    // @ExcelColumn(name = "部门")
    // private String department;

    @Schema(description = "员工编号")
    @ExcelColumn(name = "员工编号")
    private String code;

    @Schema(description = "出勤时间")
    @ExcelColumn(name = "出勤时间")
    private LocalDateTime chuQinTime;

    public UpdateDoorCommand toUpdateDoorCommand() {
        UpdateDoorCommand command = new UpdateDoorCommand();
        command.setName(name);
        command.setDoorDate(chuQinTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        QueryWrapper<DoorEntity> queryWrapper = new QueryWrapper<>();
        // 查询当天的人员档案

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String startTime = chuQinTime.toLocalDate().atStartOfDay().format(formatter);
        String endTime = chuQinTime.toLocalDate().plusDays(1).atStartOfDay().format(formatter);
        System.out.println("chuQinTime: " + startTime);
        System.out.println("chuQinTime: " + endTime);
        queryWrapper
                .inSql("personnel_id", "select personnel_id from manage_personnel where name = '" + name + "'" +
                        " and code = '" + code + "'")
                // 查询创建时间在 chuQinTime当天的记录
                .geSql("create_time", "DATE_FORMAT('" + startTime + "', '%Y-%m-%d %H:%i:%s')")
                .leSql("create_time", "DATE_FORMAT('" + endTime + "', '%Y-%m-%d %H:%i:%s')");

        DoorEntity dEntity = new DoorEntity().selectOne(queryWrapper);
        if (dEntity == null) {
            throw new ApiException(Business.PERSONNOT_SERVICE_UNAVAILABLE, getName() + getCode() + "当天记录不存在");
        }
        command.setPersonnelId(dEntity.getPersonnelId());
        command.setDoorId(new DoorEntity().selectOne(queryWrapper).getDoorId());
        // command.setDepartment(department);

        return command;
    }
}
