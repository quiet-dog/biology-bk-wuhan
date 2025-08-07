package com.biology.domain.manage.xlFangAn.db;

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
@TableName(value = "manage_xl_ceping_fangan", autoResultMap = true)
@ApiModel(value = "ReceiveEntity对象", description = "生命设备表")
public class XlFangAnEntity extends BaseEntity<XlFangAnEntity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("xl_fang_an_id")
    @TableId(value = "xl_fang_an_id", type = IdType.AUTO)
    private Long xlFangAnId;

    @TableField(value = "name")
    private String name;

    @TableField(value = "shi_juan_types", typeHandler = JacksonTypeHandler.class)
    private List<String> shiJuanTypes;

    @TableField(value = "user_ids", typeHandler = JacksonTypeHandler.class)
    private List<Integer> userIds;

    @Override
    public Serializable pkVal() {
        return this.xlFangAnId;
    }
}
