package com.biology.domain.manage.equipment;

import org.springframework.stereotype.Service;
import com.biology.domain.manage.equipment.command.AddEquipmentInspectionManualCommand;
import com.biology.domain.manage.equipment.command.UpdateEquipmentInspectionManualCommand;
import com.biology.domain.manage.equipment.dto.EquipmentDTO;
import com.biology.domain.manage.equipment.dto.EquipmentInspectionManualDTO;
import com.biology.domain.manage.equipment.model.EquipmentFactory;
import com.biology.domain.manage.equipment.model.EquipmentInspectionManualFactory;
import com.biology.domain.manage.equipment.model.EquipmentInspectionManualModel;
import com.biology.domain.manage.equipment.model.EquipmentModel;
import com.biology.domain.manage.equipment.query.SearchEquipmentInspectionManualQuery;

import cn.hutool.core.collection.CollectionUtil;

import com.biology.domain.manage.equipment.db.EquipmentInspectionManualEntity;
import com.biology.domain.manage.equipment.db.EquipmentInspectionManualService;
import com.biology.domain.manage.equipment.db.EquipmentService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquipmentInspectionManualApplicationService {

    private final EquipmentInspectionManualFactory equipmentInspectionManualFactory;
    private final EquipmentInspectionManualService equipmentInspectionManualService;
    private final EquipmentFactory equipmentFactory;

    
    public void createInspectionManual(AddEquipmentInspectionManualCommand command) {
        EquipmentInspectionManualModel manualModel = equipmentInspectionManualFactory.create();
        if (command.getManualCode() == null || command.getManualCode().isEmpty()) {
            manualModel.setManualCode(generateSerialNumber());
        } else {
            manualModel.setManualCode(command.getManualCode());
        }
        manualModel.loadAddCommand(command);
        manualModel.insert();
    }

    // 开关启用状态
    public void toggleEnableStatus(Long manualId, Boolean isEnabled) {
        EquipmentInspectionManualModel manualModel = equipmentInspectionManualFactory.loadById(manualId);
        manualModel.setIsEnabled(isEnabled);
        manualModel.update();
    }

    public void updateInspectionManual(UpdateEquipmentInspectionManualCommand command) {
        EquipmentInspectionManualModel manualModel = equipmentInspectionManualFactory.loadById(command.getManualId());
        if (command.getManualCode() == null || command.getManualCode().isEmpty()) {
            manualModel.setManualCode(generateSerialNumber());
        } else {
            manualModel.setManualCode(command.getManualCode());
        }
        manualModel.loadUpdateCommand(command);
        manualModel.update();
    }

    public void deleteInspectionManuals(List<Long> manualIds) {
        for (Long manualId : manualIds) {
            EquipmentInspectionManualModel manualModel = equipmentInspectionManualFactory.loadById(manualId);
            manualModel.deleteById();
        }
    }

    public PageDTO<EquipmentInspectionManualDTO> getInspectionManualList(SearchEquipmentInspectionManualQuery query) {
        if (CollectionUtil.isNotEmpty(query.getIds())) {
            query.setPageNum(1);
            query.setPageSize(query.getIds().size());
        }

        Page<EquipmentInspectionManualEntity> page = equipmentInspectionManualService.page(query.toPage(), query.toQueryWrapper());
        List<EquipmentInspectionManualDTO> records = page.getRecords().stream().map(entity -> {
            EquipmentInspectionManualDTO dto = new EquipmentInspectionManualDTO(entity);
            // 通过equipmentId查询equipment表获取设备
            EquipmentModel equipmentModel = equipmentFactory.loadById(entity.getEquipmentId());
            dto.setEquipment(new EquipmentDTO(equipmentModel));
            return dto;
        }).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public EquipmentInspectionManualDTO getInspectionManualInfo(Long manualId) {
        EquipmentInspectionManualModel manualModel = equipmentInspectionManualFactory.loadById(manualId);
        EquipmentInspectionManualDTO dto = new EquipmentInspectionManualDTO(manualModel);
        // 通过equipmentId查询equipment表获取设备
        EquipmentModel equipmentModel = equipmentFactory.loadById(manualModel.getEquipmentId());
        dto.setEquipment(new EquipmentDTO(equipmentModel));
        return dto;
    }

    private String generateSerialNumber() {
        return "SN-" + System.currentTimeMillis();
    }
} 