package com.biology.domain.manage.craftdisposemanual;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.craftdisposemanual.command.AddDisposeManualCommand;
import com.biology.domain.manage.craftdisposemanual.command.UpdateDisposeManualCommand;
import com.biology.domain.manage.craftdisposemanual.db.DisposeManualEntity;
import com.biology.domain.manage.craftdisposemanual.db.DisposeManualService;
import com.biology.domain.manage.craftdisposemanual.dto.DisposeManualDTO;
import com.biology.domain.manage.craftdisposemanual.model.DisposeManualFactory;
import com.biology.domain.manage.craftdisposemanual.model.DisposeManualModel;
import com.biology.domain.manage.craftdisposemanual.query.SearchDisposeManualQuery;
import com.biology.domain.manage.craftnode.db.CraftNodeEntity;
import com.biology.domain.manage.craftnode.db.CraftNodeService;
import com.biology.domain.manage.craftnode.dto.CraftNodeDTO;
import com.biology.domain.manage.craftarchive.db.CraftArchiveEntity;
import com.biology.domain.manage.craftarchive.db.CraftArchiveService;
import com.biology.domain.manage.craftarchive.dto.CraftArchiveDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CraftDisposeManualApplicationService {

    private final DisposeManualFactory disposeManualFactory;
    private final DisposeManualService disposeManualService;
    private final CraftNodeService craftNodeService;
    private final CraftArchiveService craftArchiveService;

    public void addDisposeManual(AddDisposeManualCommand command) {
        DisposeManualModel disposeManualModel = disposeManualFactory.create();
        disposeManualModel.loadAddCommand(command);
        disposeManualModel.insert();
    }

    public void updateDisposeManual(UpdateDisposeManualCommand command) {
        DisposeManualModel disposeManualModel = disposeManualFactory.loadById(command.getCraftDisposeManualId());
        disposeManualModel.loadUpdateCommand(command);
        disposeManualModel.update();
    }

    public void deleteDisposeManuals(List<Long> disposeManualIds) {
        for (Long disposeManualId : disposeManualIds) {
            DisposeManualModel disposeManualModel = disposeManualFactory.loadById(disposeManualId);
            disposeManualModel.deleteById();
        }
    }

    public DisposeManualDTO getDisposeManualInfo(Long disposeManualId) {
        DisposeManualModel disposeManualModel = disposeManualFactory.loadById(disposeManualId);
        DisposeManualDTO disposeManualDTO = new DisposeManualDTO(disposeManualModel);
        CraftNodeEntity craftNode = craftNodeService.getById(disposeManualModel.getCraftNodeId());
        if (craftNode != null) {
            disposeManualDTO.setCraftNode(new CraftNodeDTO(craftNode));
        }
        CraftArchiveEntity craftArchive = craftArchiveService.getById(disposeManualModel.getCraftArchiveId());
        if (craftArchive != null) {
            disposeManualDTO.setCraftArchive(new CraftArchiveDTO(craftArchive));
        }
        return disposeManualDTO;
    }

    public PageDTO<DisposeManualDTO> getDisposeManualList(SearchDisposeManualQuery query) {
        Page<DisposeManualEntity> page = disposeManualService.page(query.toPage(), query.toQueryWrapper());
        List<DisposeManualDTO> records = page.getRecords().stream().map(entity -> {
            DisposeManualDTO dto = new DisposeManualDTO(entity);
            CraftNodeEntity craftNode = craftNodeService.getById(entity.getCraftNodeId());
            if (craftNode != null) {
                dto.setCraftNode(new CraftNodeDTO(craftNode));
            }

            CraftArchiveEntity craftArchive = craftArchiveService.getById(entity.getCraftArchiveId());
            if (craftArchive != null) {
                dto.setCraftArchive(new CraftArchiveDTO(craftArchive));
            }
            return dto;
        }).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }
}