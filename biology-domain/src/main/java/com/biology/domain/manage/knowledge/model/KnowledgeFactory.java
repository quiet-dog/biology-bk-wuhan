package com.biology.domain.manage.knowledge.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.knowledge.db.KnowledgeEntity;
import com.biology.domain.manage.knowledge.db.KnowledgeFileService;
import com.biology.domain.manage.knowledge.db.KnowledgeService;
import com.biology.domain.manage.knowledge.db.KnowledgeTypeService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KnowledgeFactory {

    private final KnowledgeService knowledgeService;

    private final KnowledgeTypeService knowledgeTypeService;

    private final KnowledgeFileService knowledgeFileService;

    public KnowledgeModel create() {
        return new KnowledgeModel(knowledgeService, knowledgeTypeService, knowledgeFileService);
    }

    public KnowledgeModel loadById(Long knowledgeId) {

        KnowledgeEntity knowledgeModel = knowledgeService.getById(knowledgeId);

        if (knowledgeModel == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, knowledgeId, "知识点");
        }

        return new KnowledgeModel(knowledgeModel, knowledgeService, knowledgeTypeService, knowledgeFileService);
    }
}
