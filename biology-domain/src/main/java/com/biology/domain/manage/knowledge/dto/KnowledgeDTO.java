package com.biology.domain.manage.knowledge.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.knowledge.db.KnowledgeEntity;
import com.biology.domain.manage.knowledge.db.KnowledgeFileEntity;
import com.biology.domain.manage.knowledge.db.KnowledgeTypeEntity;
import com.biology.domain.system.user.db.SysUserEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class KnowledgeDTO {

    @Schema(description = "知识库ID")
    private Long knowledgeId;

    @Schema(description = "知识库类型")
    private String knowledgeType;

    @Schema(description = "知识库类型名称")
    private String knowledgeTypeName;

    @Schema(description = "知识库编号")
    private String code;

    @Schema(description = "知识库标题")
    private String title;

    @Schema(description = "浏览次数")
    private Integer viewCount;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "创建者")
    private String createrName;

    @Schema(description = "创建者ID")
    private Long creatorId;

    private List<String> paths;

    public KnowledgeDTO(KnowledgeEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
            addPaths();
            addCreaterName();
        }
    }

    public void addPaths() {
        QueryWrapper<KnowledgeFileEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("knowledge_id", knowledgeId);
        List<KnowledgeFileEntity> knowledgeFileEntities = new KnowledgeFileEntity().selectList(queryWrapper);
        paths = new ArrayList<>();
        if (knowledgeFileEntities != null && !knowledgeFileEntities.isEmpty()) {
            for (KnowledgeFileEntity knowledgeFileEntity : knowledgeFileEntities) {
                paths.add(knowledgeFileEntity.getPath());
            }
        }
    }

    public void addCreaterName() {
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", creatorId);
        SysUserEntity sysUserEntity = new SysUserEntity().selectOne(queryWrapper);
        if (sysUserEntity != null) {
            createrName = sysUserEntity.getUsername();
        }
    }

}
