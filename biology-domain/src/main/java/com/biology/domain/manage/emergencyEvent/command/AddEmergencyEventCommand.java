package com.biology.domain.manage.emergencyEvent.command;

import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddEmergencyEventCommand {

    @Schema(description = "事件名称")
    private String eventName;

    @Schema(description = "事件编号")
    private String code;

    @Schema(description = "事件内容")
    private String content;

    @Schema(description = "事件类型")
    private String type;

    @Schema(description = "处理流程")
    private String processingFlow;

    @Schema(description = "处理人IDs")
    private List<Long> handleIds;

    @Schema(description = "报警信息Ids")
    private List<Long> emergencyAlarmIds;
}
