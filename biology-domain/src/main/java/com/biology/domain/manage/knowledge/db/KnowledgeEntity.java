package com.biology.domain.manage.knowledge.db;

import java.io.Serializable;

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
@TableName("manage_knowledge")
@ApiModel(value = "KnowledgeEntity对象", description = "知识库信息表")
public class KnowledgeEntity extends BaseEntity<KnowledgeEntity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("知识库ID")
    @TableId(value = "knowledge_id", type = IdType.AUTO)
    private Long knowledgeId;

    @ApiModelProperty("知识库类型")
    @TableField(value = "knowledge_type")
    private String knowledgeType;

    @ApiModelProperty("知识库编号")
    @TableField(value = "code")
    private String code;

    @ApiModelProperty("知识库标题")
    @TableField(value = "title")
    private String title;

    @ApiModelProperty("浏览次数")
    @TableField(value = "view_count")
    private Integer viewCount;

    @Override
    public Serializable pkVal() {
        return this.knowledgeId;
    }
}
