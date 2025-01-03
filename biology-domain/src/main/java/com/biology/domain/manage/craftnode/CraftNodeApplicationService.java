package com.biology.domain.manage.craftnode;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.craftarchive.db.CraftArchiveEntity;
import com.biology.domain.manage.craftarchive.db.CraftArchiveService;
import com.biology.domain.manage.craftarchive.dto.CraftArchiveDTO;
import com.biology.domain.manage.craftnode.command.AddCraftNodeCommand;
import com.biology.domain.manage.craftnode.command.UpdateCraftNodeCommand;
import com.biology.domain.manage.craftnode.db.CraftNodeEntity;
import com.biology.domain.manage.craftnode.db.CraftNodeEquipmentService;
import com.biology.domain.manage.craftnode.db.CraftNodeService;
import com.biology.domain.manage.craftnode.dto.CraftNodeDTO;
import com.biology.domain.manage.craftnode.model.CraftNodeFactory;
import com.biology.domain.manage.craftnode.model.CraftNodeModel;
import com.biology.domain.manage.craftnode.query.SearchCraftNodeQuery;
import com.biology.domain.manage.equipment.db.EquipmentEntity;
import com.biology.domain.manage.equipment.db.EquipmentService;
import com.biology.domain.manage.equipment.dto.EquipmentDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CraftNodeApplicationService {

    private final CraftArchiveService craftArchiveService;
    private final CraftNodeService craftNodeService;
    private final CraftNodeFactory craftNodeFactory;
    private final CraftNodeEquipmentService craftNodeEquipmentService;
    private final EquipmentService equipmentService;

    public PageDTO<CraftNodeDTO> getCraftNodeList(SearchCraftNodeQuery query) {
        // 分页查询工艺节点
        Page<CraftNodeEntity> page = craftNodeService.page(query.toPage(), query.toQueryWrapper());

        if (page == null || page.getRecords().isEmpty()) {
            return new PageDTO<>(new ArrayList<>(), 0L);
        }

        List<CraftNodeDTO> records = page.getRecords().stream()
                .map(CraftNodeDTO::new)
                .collect(Collectors.toList());

        // 填充关联数据
        fillCraftArchives(records);
        fillEquipments(records);

        return new PageDTO<>(records, page.getTotal());
    }

    // 新增私有方法处理工艺档案数据填充
    private void fillCraftArchives(List<CraftNodeDTO> records) {
        List<Long> archiveIds = records.stream()
                .map(CraftNodeDTO::getCraftArchiveId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (!archiveIds.isEmpty()) {
            Map<Long, CraftArchiveEntity> archiveMap = craftArchiveService.listByIds(archiveIds).stream()
                    .collect(Collectors.toMap(CraftArchiveEntity::getCraftArchiveId, Function.identity()));

            records.forEach(dto -> Optional.ofNullable(dto.getCraftArchiveId())
                    .map(archiveMap::get)
                    .ifPresent(archive -> dto.setCraftArchive(new CraftArchiveDTO(archive))));
        }
    }

    // 新增私有方法处理设备数据填充
    private void fillEquipments(List<CraftNodeDTO> records) {
        List<Long> nodeIds = records.stream()
                .map(CraftNodeDTO::getCraftNodeId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (!nodeIds.isEmpty()) {
            Map<Long, List<Long>> nodeEquipmentMap = nodeIds.stream()
                    .collect(Collectors.toMap(
                            nodeId -> nodeId,
                            craftNodeEquipmentService::getEquipmentIdsByCraftNodeId));

            List<Long> equipmentIds = nodeEquipmentMap.values().stream()
                    .flatMap(List::stream)
                    .distinct()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            if (!equipmentIds.isEmpty()) {
                Map<Long, EquipmentEntity> equipmentMap = equipmentService.listByEquipmentIds(equipmentIds).stream()
                        .collect(Collectors.toMap(EquipmentEntity::getEquipmentId, Function.identity()));

                records.forEach(dto -> {
                    List<Long> nodeEquipmentIds = nodeEquipmentMap.get(dto.getCraftNodeId());
                    if (nodeEquipmentIds != null && !nodeEquipmentIds.isEmpty()) {
                        dto.setEquipmentIds(nodeEquipmentIds);
                        dto.setEquipmentList(nodeEquipmentIds.stream()
                                .map(equipmentMap::get)
                                .filter(Objects::nonNull)
                                .map(EquipmentDTO::new)
                                .collect(Collectors.toList()));
                    }
                });
            }
        }
    }

    public CraftNodeDTO getCraftNodeInfo(Long nodeId) {
        CraftNodeModel craftNodeModel = craftNodeFactory.loadById(nodeId);
        CraftNodeDTO dto = new CraftNodeDTO(craftNodeModel);

        // 查询关联的工艺档案
        Long craftArchiveId = craftNodeModel.getCraftArchiveId();
        if (craftArchiveId != null) {
            CraftArchiveEntity craftArchive = craftArchiveService.getById(craftArchiveId);
            dto.setCraftArchive(new CraftArchiveDTO(craftArchive));
        }

        // 查询关联的设备列表
        List<Long> equipmentIds = craftNodeEquipmentService.getEquipmentIdsByCraftNodeId(nodeId);
        dto.setEquipmentIds(equipmentIds);
        System.out.println("equipmentIds: " + equipmentIds);
        System.out.println("After setting equipmentIds: " + dto.getEquipmentIds());

        if (!equipmentIds.isEmpty()) {
            List<EquipmentEntity> equipmentList = equipmentService.listByEquipmentIds(equipmentIds);
            dto.setEquipmentList(equipmentList.stream()
                    .map(EquipmentDTO::new)
                    .collect(Collectors.toList()));

            System.out.println("equipmentList: " + dto.getEquipmentList());
        }
        return dto;
    }

    // @Transactional(rollbackFor = Exception.class)
    public void addCraftNode(AddCraftNodeCommand command) {
        CraftNodeModel craftNodeModel = craftNodeFactory.create();
        craftNodeModel.loadAddCommand(command);
        craftNodeModel.insert();
    }

    // @Transactional(rollbackFor = Exception.class)
    public void updateCraftNode(UpdateCraftNodeCommand command) {
        CraftNodeModel craftNodeModel = craftNodeFactory.loadById(command.getNodeId());
        craftNodeModel.loadUpdateCommand(command);
        craftNodeModel.updateById();
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteCraftNodes(List<Long> nodeIds) {
        for (Long nodeId : nodeIds) {
            CraftNodeModel craftNodeModel = craftNodeFactory.loadById(nodeId);
            craftNodeModel.deleteById();
        }
    }
}
