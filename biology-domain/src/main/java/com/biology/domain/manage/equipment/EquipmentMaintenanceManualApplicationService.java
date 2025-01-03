package com.biology.domain.manage.equipment;

import org.springframework.stereotype.Service;
import com.biology.domain.manage.equipment.command.AddEquipmentMaintenanceManualCommand;
import com.biology.domain.manage.equipment.command.UpdateEquipmentMaintenanceManualCommand;
import com.biology.domain.manage.equipment.dto.EquipmentDTO;
import com.biology.domain.manage.equipment.dto.EquipmentMaintenanceManualDTO;
import com.biology.domain.manage.equipment.model.EquipmentFactory;
import com.biology.domain.manage.equipment.model.EquipmentMaintenanceManualFactory;
import com.biology.domain.manage.equipment.model.EquipmentMaintenanceManualModel;
import com.biology.domain.manage.equipment.model.EquipmentModel;
import com.biology.domain.manage.equipment.query.SearchEquipmentMaintenanceManualQuery;

import cn.hutool.core.collection.CollectionUtil;

import com.biology.domain.manage.equipment.db.EquipmentMaintenanceManualEntity;
import com.biology.domain.manage.equipment.db.EquipmentMaintenanceManualService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquipmentMaintenanceManualApplicationService {

    private final EquipmentMaintenanceManualFactory equipmentMaintenanceManualFactory;
    private final EquipmentMaintenanceManualService equipmentMaintenanceManualService;
    private final EquipmentFactory equipmentFactory;

    public void createMaintenanceManual(AddEquipmentMaintenanceManualCommand command) {
        EquipmentMaintenanceManualModel manualModel = equipmentMaintenanceManualFactory.create();
        if (command.getManualCode() == null || command.getManualCode().isEmpty()) {
            manualModel.setManualCode(generateSerialNumber()); // 自动生成编号
        } else {
            manualModel.setManualCode(command.getManualCode());
        }
        manualModel.loadAddCommand(command);
        manualModel.insert();
    }

    public void updateMaintenanceManual(UpdateEquipmentMaintenanceManualCommand command) {
        EquipmentMaintenanceManualModel manualModel = equipmentMaintenanceManualFactory.loadById(command.getManualId());
        if (command.getManualCode() == null || command.getManualCode().isEmpty()) {
            manualModel.setManualCode(generateSerialNumber()); // 自动生成编号
        } else {
            manualModel.setManualCode(command.getManualCode());
        }
        manualModel.loadUpdateCommand(command);
        manualModel.update();
    }

    public void deleteMaintenanceManuals(List<Long> manualIds) {
        for (Long manualId : manualIds) {
            EquipmentMaintenanceManualModel manualModel = equipmentMaintenanceManualFactory.loadById(manualId);
            manualModel.deleteById();
        }
    }

    public PageDTO<EquipmentMaintenanceManualDTO> getMaintenanceManualList(SearchEquipmentMaintenanceManualQuery query) {
        if (CollectionUtil.isNotEmpty(query.getIds())) {
            query.setPageNum(1);
            query.setPageSize(query.getIds().size());
        }

        Page<EquipmentMaintenanceManualEntity> page = equipmentMaintenanceManualService.page(query.toPage(), query.toQueryWrapper());
        List<EquipmentMaintenanceManualDTO> records = page.getRecords().stream().map(entity -> {
            EquipmentMaintenanceManualDTO dto = new EquipmentMaintenanceManualDTO(entity);
            EquipmentModel equipmentModel = equipmentFactory.loadById(entity.getEquipmentId());
            dto.setEquipment(new EquipmentDTO(equipmentModel));
            return dto;
        }).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public EquipmentMaintenanceManualDTO getMaintenanceManualInfo(Long manualId) {
        EquipmentMaintenanceManualModel manualModel = equipmentMaintenanceManualFactory.loadById(manualId);
        EquipmentMaintenanceManualDTO dto = new EquipmentMaintenanceManualDTO(manualModel);
        EquipmentModel equipmentModel = equipmentFactory.loadById(manualModel.getEquipmentId());
        dto.setEquipment(new EquipmentDTO(equipmentModel));
        return dto;
    }

    // 添加一个生成编号的方法
    private String generateSerialNumber() {
        return "SN-" + System.currentTimeMillis(); // 简单的示例，使用当前时间戳生成唯一编号
    }
} 