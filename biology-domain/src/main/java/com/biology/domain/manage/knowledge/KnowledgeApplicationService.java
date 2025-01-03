package com.biology.domain.manage.knowledge;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.knowledge.command.AddKnowledgeCommand;
import com.biology.domain.manage.knowledge.command.AddKnowledgeTypeCommand;
import com.biology.domain.manage.knowledge.command.UpdateKnowledgeCommand;
import com.biology.domain.manage.knowledge.db.KnowledgeEntity;
import com.biology.domain.manage.knowledge.db.KnowledgeService;
import com.biology.domain.manage.knowledge.dto.KnowledgeDTO;
import com.biology.domain.manage.knowledge.model.KnowledgeFactory;
import com.biology.domain.manage.knowledge.model.KnowledgeModel;
import com.biology.domain.manage.knowledge.model.KnowledgeTypeFactory;
import com.biology.domain.manage.knowledge.model.KnowledgeTypeModel;
import com.biology.domain.manage.knowledge.query.SearchKnowledgeQuery;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KnowledgeApplicationService {

    private final KnowledgeFactory knowledgeFactory;

    private final KnowledgeService knowledgeService;

    private final KnowledgeTypeFactory knowledgeTypeFactory;

    public void addKnowledge(AddKnowledgeCommand command) {
        KnowledgeModel knowledgeModel = knowledgeFactory.create();
        knowledgeModel.loadAddKnowledgeCommand(command);
        // knowledgeModel.checkKnowledgeType();
        knowledgeModel.insert();
    }

    public void updateKnowledge(UpdateKnowledgeCommand command) {
        KnowledgeModel knowledgeModel = knowledgeFactory.loadById(command.getKnowledgeId());
        knowledgeModel.loadAddKnowledgeCommand(command);
        // knowledgeModel.checkKnowledgeType();
        knowledgeModel.update();
    }

    public void deleteKnowledges(List<Long> knowledgeIds) {
        for (Long knowledgeId : knowledgeIds) {
            KnowledgeModel knowledgeModel = knowledgeFactory.loadById(knowledgeId);
            knowledgeModel.deleteById();
        }
    }

    public PageDTO<KnowledgeDTO> getKnowledgeList(SearchKnowledgeQuery query) {
        Page<KnowledgeEntity> page = knowledgeService.page(query.toPage(), query.toQueryWrapper());
        List<KnowledgeDTO> records = page.getRecords().stream().map(KnowledgeDTO::new).collect(Collectors.toList());
        for (KnowledgeDTO record : records) {
            record.setPaths(knowledgeService.getKnowledgePaths(record.getKnowledgeId()));
            record.setCreaterName(knowledgeService.getCreater(record.getCreatorId()).getUsername());
        }
        return new PageDTO<>(records, page.getTotal());
    }

    public KnowledgeDTO getKnowledgeInfo(Long knowledgeId) {
        KnowledgeModel knowledgeModel = knowledgeFactory.loadById(knowledgeId);
        return new KnowledgeDTO(knowledgeModel);
    }

    public void addKnowledgeType(AddKnowledgeTypeCommand command) {
        KnowledgeTypeModel knowledgeTypeModel = knowledgeTypeFactory.create();
        knowledgeTypeModel.loadAddKnowledgeTypeCommand(command);
        knowledgeTypeModel.insert();
    }

}
