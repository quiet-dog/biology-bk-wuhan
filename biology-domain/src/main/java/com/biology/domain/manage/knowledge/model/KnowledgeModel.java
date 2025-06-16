package com.biology.domain.manage.knowledge.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.analysis.function.Add;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.biology.domain.manage.knowledge.command.AddKnowledgeCommand;
import com.biology.domain.manage.knowledge.db.KnowledgeEntity;
import com.biology.domain.manage.knowledge.db.KnowledgeFileEntity;
import com.biology.domain.manage.knowledge.db.KnowledgeFileService;
import com.biology.domain.manage.knowledge.db.KnowledgeService;
import com.biology.domain.manage.knowledge.db.KnowledgeTypeEntity;
import com.biology.domain.manage.knowledge.db.KnowledgeTypeService;
import com.biology.domain.system.role.db.SysRoleMenuEntity;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class KnowledgeModel extends KnowledgeEntity {

    private List<String> paths;

    private KnowledgeService knowledgeService;

    private KnowledgeTypeService knowledgeTypeService;

    private KnowledgeFileService knowledgeFileService;

    public KnowledgeModel(KnowledgeService knowledgeService, KnowledgeTypeService knowledgeTypeService,
            KnowledgeFileService knowledgeFileService) {
        this.knowledgeService = knowledgeService;
        this.knowledgeTypeService = knowledgeTypeService;
        this.knowledgeFileService = knowledgeFileService;
    }

    public KnowledgeModel(KnowledgeEntity entity, KnowledgeService knowledgeService,
            KnowledgeTypeService knowledgeTypeService, KnowledgeFileService knowledgeFileService) {

        this(knowledgeService, knowledgeTypeService, knowledgeFileService);

        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
    }

    public void loadAddKnowledgeCommand(AddKnowledgeCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "knowledgeId");
        }
    }

    // public void checkKnowledgeType() {
    // KnowledgeTypeEntity knowledgeType =
    // knowledgeTypeService.getById(getKnowledgeTypeId());
    // if (knowledgeType == null) {
    // throw new RuntimeException("知识库类型不存在");
    // }
    // }

    public boolean insert() {
        super.insert();
        return savePath();
    }

    public boolean update() {
        cleanOldPath();
        super.updateById();
        return savePath();
    }

    public boolean deleteById() {
        cleanOldPath();
        return super.deleteById();
    }

    public boolean savePath() {
        List<KnowledgeFileEntity> kts = new ArrayList<>();
        for (String path : paths) {
            KnowledgeFileEntity kt = new KnowledgeFileEntity();
            kt.setPath(path);
            kt.setKnowledgeId(getKnowledgeId());
            kts.add(kt);
        }
        return knowledgeFileService.saveBatch(kts);
    }

    public void cleanOldPath() {
        LambdaQueryWrapper<KnowledgeFileEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(KnowledgeFileEntity::getKnowledgeId, getKnowledgeId());
        knowledgeFileService.remove(queryWrapper);
    }
}
