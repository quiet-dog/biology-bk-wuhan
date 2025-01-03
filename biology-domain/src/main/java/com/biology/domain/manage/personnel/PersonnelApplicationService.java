package com.biology.domain.manage.personnel;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.knowledge.db.KnowledgeEntity;
import com.biology.domain.manage.knowledge.dto.KnowledgeDTO;
import com.biology.domain.manage.personnel.command.AddPersonnelCommand;
import com.biology.domain.manage.personnel.command.UpdatePersonnelCommand;
import com.biology.domain.manage.personnel.db.PersonnelEntity;
import com.biology.domain.manage.personnel.db.PersonnelService;
import com.biology.domain.manage.personnel.dto.PersonnelDTO;
import com.biology.domain.manage.personnel.model.PersonnelFactory;
import com.biology.domain.manage.personnel.model.PersonnelModel;
import com.biology.domain.manage.personnel.query.PersonnelQuery;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonnelApplicationService {
    private final PersonnelFactory personnelFactory;

    private final PersonnelService personnelService;

    public void create(AddPersonnelCommand command) {
        PersonnelModel personnelModel = personnelFactory.create();
        personnelModel.loadAddPersonnelCommand(command);
        personnelModel.insert();
        return;
    }

    public void update(UpdatePersonnelCommand command) {
        PersonnelModel personnelModel = personnelFactory.loadById(command.getPersonnelId());
        personnelModel.loadUpdatePersonnelCommand(command);
        personnelModel.updateById();
        return;
    }

    public void deletePersonnels(List<Long> personnelIds) {
        personnelIds.forEach(personnelId -> {
            PersonnelModel personnelModel = personnelFactory.loadById(personnelId);
            personnelModel.deleteById();
        });
        return;
    }

    public PersonnelDTO getPersonnel(Long personnelId) {
        PersonnelModel personnelModel = personnelFactory.loadById(personnelId);
        return new PersonnelDTO(personnelModel);
    }

    public PageDTO<PersonnelDTO> getPersonnels(PersonnelQuery query) {
        Page<PersonnelEntity> page = personnelService.page(query.toPage(), query.toQueryWrapper());
        List<PersonnelDTO> records = page.getRecords().stream().map(PersonnelDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }
}
