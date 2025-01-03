package com.biology.domain.manage.knowledge.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_knowledge_file")
@ApiModel(value = "KnowledgeFileEntity对象", description = "知识库文件信息表")
public class KnowledgeFileEntity extends Model<KnowledgeFileEntity> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "knowledge_id", type = IdType.AUTO)
    private Long knowledgeId;

    @TableField(value = "path")
    private String path;

    @Override
    public Long pkVal() {
        return this.knowledgeId;
    }
}
