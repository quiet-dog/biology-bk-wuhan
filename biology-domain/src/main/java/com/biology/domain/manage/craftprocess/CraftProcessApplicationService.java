package com.biology.domain.manage.craftprocess;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode;
import com.biology.domain.manage.craftprocess.command.AddCraftProcessCommand;
import com.biology.domain.manage.craftprocess.command.UpdateCraftProcessCommand;
import com.biology.domain.manage.craftprocess.db.CraftProcessEntity;
import com.biology.domain.manage.craftprocess.db.CraftProcessService;
import com.biology.domain.manage.craftprocess.dto.CraftProcessDTO;
import com.biology.domain.manage.craftprocess.query.SearchCraftProcessQuery;
import com.biology.domain.manage.craftarchive.db.CraftArchiveEntity;
import com.biology.domain.manage.craftarchive.db.CraftArchiveService;
import com.biology.domain.manage.craftarchive.dto.CraftArchiveDTO;
import com.biology.domain.manage.craftnode.db.CraftNodeEntity;
import com.biology.domain.manage.craftnode.db.CraftNodeService;
import com.biology.domain.manage.craftnode.dto.CraftNodeDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CraftProcessApplicationService {

    private final CraftProcessService craftProcessService;
    private final CraftArchiveService craftArchiveService;
    private final CraftNodeService craftNodeService;

    
    public PageDTO<CraftProcessDTO> getCraftProcessList(SearchCraftProcessQuery query) {
        Page<CraftProcessEntity> page = craftProcessService.page(query.toPage(), query.toQueryWrapper());
        List<CraftProcessDTO> records = page.getRecords().stream().map(entity -> {
            CraftProcessDTO dto = new CraftProcessDTO(entity);
            CraftArchiveEntity craftArchive = craftArchiveService.getById(entity.getCraftArchiveId());
            if (craftArchive != null) {
                dto.setCraftArchiveName(craftArchive.getCraftArchiveName());
                dto.setCraftArchive(new CraftArchiveDTO(craftArchive));
            }
            CraftNodeEntity craftNode = craftNodeService.getById(entity.getCraftNodeId());
            if (craftNode != null) {
                dto.setCraftNode(new CraftNodeDTO(craftNode));
            }
            return dto;
        }).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public CraftProcessDTO getCraftProcessInfo(Long craftProcessId) {
        CraftProcessEntity entity = craftProcessService.getById(craftProcessId);
        if (entity == null) {
            throw new ApiException(ErrorCode.Business.COMMON_OBJECT_NOT_FOUND, craftProcessId, "工艺流程图");
        }
        CraftProcessDTO dto = new CraftProcessDTO(entity);
        CraftArchiveEntity craftArchive = craftArchiveService.getById(entity.getCraftArchiveId());
        if (craftArchive != null) {
            dto.setCraftArchiveName(craftArchive.getCraftArchiveName());
            dto.setCraftArchive(new CraftArchiveDTO(craftArchive));
        }
        CraftNodeEntity craftNode = craftNodeService.getById(entity.getCraftNodeId());
        if (craftNode != null) {
            dto.setCraftNode(new CraftNodeDTO(craftNode));
        }
        return dto;
    }

    @Transactional(rollbackFor = Exception.class)
    public void addCraftProcess(AddCraftProcessCommand command) {
        CraftProcessEntity entity = new CraftProcessEntity();
        entity.setCraftArchiveId(command.getCraftArchiveId());
        entity.setCraftNodeId(command.getCraftNodeId());
        entity.setNodeOrder(command.getNodeOrder());
        entity.setPersonnelFactors(command.getPersonnelFactors());
        entity.setEquipmentFactors(command.getEquipmentFactors());
        entity.setMaterialFactors(command.getMaterialFactors());
        entity.setEnvironmentFactors(command.getEnvironmentFactors());
        craftProcessService.save(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateCraftProcess(UpdateCraftProcessCommand command) {
        CraftProcessEntity entity = craftProcessService.getById(command.getCraftProcessId());
        if (entity == null) {
            throw new ApiException(ErrorCode.Business.COMMON_OBJECT_NOT_FOUND, command.getCraftProcessId(), "工艺流程图");
        }
        entity.setCraftArchiveId(command.getCraftArchiveId());
        entity.setCraftNodeId(command.getCraftNodeId());
        entity.setNodeOrder(command.getNodeOrder());
        entity.setPersonnelFactors(command.getPersonnelFactors());
        entity.setEquipmentFactors(command.getEquipmentFactors());
        entity.setMaterialFactors(command.getMaterialFactors());
        entity.setEnvironmentFactors(command.getEnvironmentFactors());
        craftProcessService.updateById(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteCraftProcesses(List<Long> craftProcessIds) {
        craftProcessService.removeByIds(craftProcessIds);
    }
}
