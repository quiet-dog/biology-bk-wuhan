package com.biology.domain.manage.knowledge.db;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "KnowledgeTypeEntity对象", description = "知识库类型表")
@TableName("manage_knowledge_type")
public class KnowledgeTypeEntity extends Model<KnowledgeTypeEntity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("知识库类型ID")
    @TableId(value = "knowledge_type_id", type = IdType.AUTO)
    private Long KnowledgeTypeId;

    @ApiModelProperty("知识库类型名称")
    @TableField(value = "type_name")
    private String typeName;

    @Override
    public Serializable pkVal() {
        return this.KnowledgeTypeId;
    }
}
