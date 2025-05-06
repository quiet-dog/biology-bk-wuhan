package com.biology.domain.manage.chuqin.command;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddChuQinCommand {

    @Schema(description = "出勤时间")
    private LocalDateTime chuQinTime;

    @Schema(description = "人员档案ID")
    private Long personnelId;
}
