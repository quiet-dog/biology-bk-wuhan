package com.biology.domain.manage.knowledge.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.knowledge.db.KnowledgeTypeEntity;
import com.biology.domain.manage.knowledge.db.KnowledgeTypeService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KnowledgeTypeFactory {

    private final KnowledgeTypeService knowledgeTypeService;

    public KnowledgeTypeModel create() {
        return new KnowledgeTypeModel(knowledgeTypeService);
    }

    public KnowledgeTypeModel loadById(Long knowledgeTypeId) {

        KnowledgeTypeEntity knowledgeTypeModel = knowledgeTypeService.getById(knowledgeTypeId);

        if (knowledgeTypeModel == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, knowledgeTypeId, "知识点类型");
        }

        return new KnowledgeTypeModel(knowledgeTypeModel, knowledgeTypeService);
    }
}
