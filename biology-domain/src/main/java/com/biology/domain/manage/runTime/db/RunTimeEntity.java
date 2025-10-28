package com.biology.domain.manage.runTime.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biology.common.core.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_run_time")
@ApiModel(value = "ReportEntity对象", description = "运行时间")
public class RunTimeEntity extends BaseEntity<RunTimeEntity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("run_time_id")
    @TableId(value = "run_time_id", type = IdType.AUTO)
    private Long runTimeId;

    @TableField(value = "equipment_id")
    private Long equipmentId;

    @TableField(value = "environment_id")
    private Long environmentId;

    @TableField(value = "is_stop")
    private Boolean isStop;

    @Override
    public Long pkVal() {
        return this.runTimeId;
    }
}
