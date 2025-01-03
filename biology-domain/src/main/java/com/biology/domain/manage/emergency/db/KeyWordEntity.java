package com.biology.domain.manage.emergency.db;

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
@ApiModel(value = "EmergencyKeyWordEntity对象", description = "应急关键词表")
@TableName("manage_keyword")
public class KeyWordEntity extends BaseEntity<KeyWordEntity> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "keyword_id", type = IdType.AUTO)
    private Long keywordId;

    @ApiModelProperty("keyword")
    @TableField(value = "keyword")
    private String keyword;

    @Override
    public Long pkVal() {
        return this.keywordId;
    }
}
