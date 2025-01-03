package com.biology.domain.manage.knowledge.model;

import com.biology.domain.manage.knowledge.command.AddKnowledgeTypeCommand;
import com.biology.domain.manage.knowledge.db.KnowledgeTypeEntity;
import com.biology.domain.manage.knowledge.db.KnowledgeTypeService;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class KnowledgeTypeModel extends KnowledgeTypeEntity {

    private KnowledgeTypeService knowledgeTypeService;

    public KnowledgeTypeModel(KnowledgeTypeService knowledgeTypeService) {
        this.knowledgeTypeService = knowledgeTypeService;
    }

    public KnowledgeTypeModel(KnowledgeTypeEntity entity, KnowledgeTypeService knowledgeTypeService) {
        this(knowledgeTypeService);
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
    }

    public void loadAddKnowledgeTypeCommand(AddKnowledgeTypeCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "knowledgeTypeId");
        }
    }
}
