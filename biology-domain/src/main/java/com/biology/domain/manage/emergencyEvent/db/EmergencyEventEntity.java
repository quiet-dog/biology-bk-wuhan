package com.biology.domain.manage.emergencyEvent.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biology.common.core.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_emergency_event")
@ApiModel(value = "EmergencyAlarmEntity对象", description = "应急调度报警事件表")
public class EmergencyEventEntity extends BaseEntity<EmergencyEventEntity> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "emergency_event_id", type = IdType.AUTO)
    private Long emergencyEventId;

    @Schema(description = "事件名称")
    @TableField(value = "event_name")
    private String eventName;

    @Schema(description = "事件编号")
    @TableField(value = "code")
    private String code;

    @Schema(description = "事件类型")
    @TableField(value = "type")
    private String type;

    @Schema(description = "处理流程")
    @TableField(value = "processing_flow")
    private String processingFlow;

    @Schema(description = "事件内容")
    @TableField(value = "content")
    private String content;

    @TableField(value = "status")
    private Boolean status;

    @TableField(value = "level")
    private String level;

    @Override
    public Long pkVal() {
        return this.emergencyEventId;
    }

}
