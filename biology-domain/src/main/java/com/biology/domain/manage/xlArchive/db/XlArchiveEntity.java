package com.biology.domain.manage.xlArchive.db;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.biology.common.core.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "manage_xl_archive", autoResultMap = true)
@ApiModel(value = "ReceiveEntity对象", description = "心理健康档案表")
public class XlArchiveEntity extends BaseEntity<XlArchiveEntity> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "xl_archive_id", type = IdType.AUTO)
    private Long xlArchiveId;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "status")
    private String status;

    @TableField(value = "personnel_id")
    private Long personnelId;

    @Override
    public Serializable pkVal() {
        return this.xlArchiveId;
    }
}
