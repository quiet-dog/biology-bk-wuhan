package com.biology.domain.manage.policies;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.event.db.EventEntity;
import com.biology.domain.manage.event.dto.EventDTO;
import com.biology.domain.manage.policies.command.AddPoliciesCommand;
import com.biology.domain.manage.policies.command.UpdatePoliciesCommand;
import com.biology.domain.manage.policies.db.PoliciesAppendixEntity;
import com.biology.domain.manage.policies.db.PoliciesEntity;
import com.biology.domain.manage.policies.db.PoliciesService;
import com.biology.domain.manage.policies.dto.PoliciesDTO;
import com.biology.domain.manage.policies.model.PoliciesModel;
import com.biology.domain.manage.policies.model.PoliciesModelFactory;
import com.biology.domain.manage.policies.query.PoliciesQuery;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PoliciesApplicationService {

    private final PoliciesModelFactory policiesModelFactory;

    private final PoliciesService policiesService;

    public void createPolicy(AddPoliciesCommand command) {
        PoliciesModel policiesModel = policiesModelFactory.create();
        policiesModel.loadAddPoliciesCommand(command);
        policiesModel.insert();
    }

    public PageDTO<PoliciesDTO> getPoliciesList(PoliciesQuery query) {

        Page<PoliciesEntity> page = policiesService.page(query.toPage(), query.toQueryWrapper());
        List<PoliciesDTO> records = page.getRecords().stream().map(PoliciesDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public PoliciesDTO getPoliciesInfo(Long policiesId) {
        PoliciesEntity byId = policiesService.getById(policiesId);
        return new PoliciesDTO(byId);
    }

    public void updatePolicies(UpdatePoliciesCommand command) {
        PoliciesModel policiesModel = policiesModelFactory.loadById(command.getPoliciesId());
        policiesModel.loadUpdatePoliciesCommand(command);
        policiesModel.updateById();
    }

    public void removeById(Long policiesId) {
        PoliciesModel policiesModel = policiesModelFactory.loadById(policiesId);
        policiesModel.deleteById();
    }

    public void deletes(List<Long> policiesIds) {
        for (Long policiesId : policiesIds) {
            PoliciesModel policiesModel = policiesModelFactory.loadById(policiesId);
            policiesModel.deleteById();
        }
    }

}
