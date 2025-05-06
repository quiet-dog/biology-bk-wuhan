package com.biology.domain.manage.chuqin.db;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@TableName(value = "manage_chuqin", autoResultMap = true)
@ApiModel(value = "出勤对象", description = "人员出勤表")
public class ChuQinEntity extends BaseEntity<ChuQinEntity> {
    @ApiModelProperty("出勤ID")
    @TableId(value = "chu_qin_id", type = IdType.AUTO)
    private Long chuQinId;

    @ApiModelProperty("出勤时间")
    @TableField("chu_qin_time")
    private LocalDateTime chuQinTime;

    @ApiModelProperty("人员档案ID")
    @TableField("personnel_id")
    private Long personnelId;

    @Override
    public Serializable pkVal() {
        return this.chuQinId;
    }
}
