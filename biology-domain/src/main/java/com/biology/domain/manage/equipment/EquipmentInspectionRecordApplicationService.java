package com.biology.domain.manage.equipment;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.equipment.command.AddEquipmentInspectionRecordCommand;
import com.biology.domain.manage.equipment.command.UpdateEquipmentInspectionRecordCommand;
import com.biology.domain.manage.equipment.db.EquipmentInspectionRecordEntity;
import com.biology.domain.manage.equipment.db.EquipmentInspectionRecordService;
import com.biology.domain.manage.equipment.dto.EquipmentDTO;
import com.biology.domain.manage.equipment.dto.EquipmentInspectionRecordDTO;
import com.biology.domain.manage.equipment.model.EquipmentFactory;
import com.biology.domain.manage.equipment.model.EquipmentInspectionRecordFactory;
import com.biology.domain.manage.equipment.model.EquipmentInspectionRecordModel;
import com.biology.domain.manage.equipment.model.EquipmentModel;
import com.biology.domain.manage.equipment.query.SearchEquipmentInspectionQuery;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EquipmentInspectionRecordApplicationService {

    private final EquipmentInspectionRecordFactory equipmentInspectionRecordFactory;
    private final EquipmentInspectionRecordService equipmentInspectionRecordService;
    private final EquipmentFactory equipmentFactory;

    public void createInspectionRecord(AddEquipmentInspectionRecordCommand command) {
        EquipmentInspectionRecordModel inspectionModel = equipmentInspectionRecordFactory.create();
        inspectionModel.loadAddCommand(command);
        if (command.getInspectionCode() == null || command.getInspectionCode().isEmpty()) {
            String randomInspectionCode = RandomStringUtils.randomAlphanumeric(8);
            inspectionModel.setInspectionCode(randomInspectionCode);
        }
        inspectionModel.insert();
    }

    public void updateInspectionRecord(UpdateEquipmentInspectionRecordCommand command) {
        EquipmentInspectionRecordModel inspectionModel = equipmentInspectionRecordFactory
                .loadById(command.getRecordId());
        inspectionModel.loadUpdateCommand(command);
        inspectionModel.update();
    }

    public void deleteInspectionRecords(List<Long> recordIds) {
        for (Long recordId : recordIds) {
            EquipmentInspectionRecordModel inspectionModel = equipmentInspectionRecordFactory.loadById(recordId);
            inspectionModel.deleteById();
        }
    }

    public PageDTO<EquipmentInspectionRecordDTO> getInspectionRecordList(SearchEquipmentInspectionQuery query) {

        Page<EquipmentInspectionRecordEntity> page = equipmentInspectionRecordService.page(query.toPage(),
                query.toQueryWrapper());
        List<EquipmentInspectionRecordDTO> records = page.getRecords().stream().map(entity -> {
            EquipmentInspectionRecordDTO dto = new EquipmentInspectionRecordDTO(entity);
            EquipmentModel equipmentModel = equipmentFactory.loadById(entity.getEquipmentId());
            dto.setEquipment(new EquipmentDTO(equipmentModel));
            return dto;
        }).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public EquipmentInspectionRecordDTO getInspectionRecordInfo(Long recordId) {
        EquipmentInspectionRecordModel inspectionModel = equipmentInspectionRecordFactory.loadById(recordId);
        EquipmentInspectionRecordDTO dto = new EquipmentInspectionRecordDTO(inspectionModel);
        EquipmentModel equipmentModel = equipmentFactory.loadById(inspectionModel.getEquipmentId());
        dto.setEquipment(new EquipmentDTO(equipmentModel));
        if (dto.getEquipment() != null) {
            dto.setEquipmentName(dto.getEquipment().getEquipmentName());
        }
        return dto;
    }
}