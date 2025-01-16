package com.biology.domain.manage.equipment;

import org.springframework.stereotype.Service;
import com.biology.domain.manage.equipment.command.AddEquipmentDailyInspectionRecordCommand;
import com.biology.domain.manage.equipment.command.UpdateEquipmentDailyInspectionRecordCommand;
import com.biology.domain.manage.equipment.dto.EquipmentDailyInspectionRecordDTO;
import com.biology.domain.manage.equipment.dto.EquipmentDataStockEchartDTO;
import com.biology.domain.manage.equipment.model.EquipmentDailyInspectionRecordFactory;
import com.biology.domain.manage.equipment.model.EquipmentDailyInspectionRecordModel;
import com.biology.domain.manage.equipment.query.SearchEquipmentDailyInspectionRecordQuery;
import com.biology.domain.manage.event.query.AreaStatisticsQuery;

import cn.hutool.core.collection.CollectionUtil;

import com.biology.domain.manage.equipment.db.EquipmentDailyInspectionRecordEntity;
import com.biology.domain.manage.equipment.db.EquipmentDailyInspectionRecordService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import lombok.RequiredArgsConstructor;

import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquipmentDailyInspectionRecordApplicationService {

    private final EquipmentDailyInspectionRecordFactory equipmentDailyInspectionRecordFactory;
    private final EquipmentDailyInspectionRecordService equipmentDailyInspectionRecordService;

    public void createDailyInspectionRecord(AddEquipmentDailyInspectionRecordCommand command) {
        EquipmentDailyInspectionRecordModel recordModel = equipmentDailyInspectionRecordFactory.create();
        if (command.getInspectionCode() == null || command.getInspectionCode().isEmpty()) {
            recordModel.setInspectionCode(generateSerialNumber());
        } else {
            recordModel.setInspectionCode(command.getInspectionCode());
        }
        recordModel.loadAddCommand(command);
        recordModel.insert();
    }

    public void updateDailyInspectionRecord(UpdateEquipmentDailyInspectionRecordCommand command) {
        EquipmentDailyInspectionRecordModel recordModel = equipmentDailyInspectionRecordFactory
                .loadById(command.getRecordId());
        recordModel.loadUpdateCommand(command);
        recordModel.update();
    }

    public void deleteDailyInspectionRecords(List<Long> recordIds) {
        for (Long recordId : recordIds) {
            EquipmentDailyInspectionRecordModel recordModel = equipmentDailyInspectionRecordFactory.loadById(recordId);
            recordModel.deleteById();
        }
    }

    public PageDTO<EquipmentDailyInspectionRecordDTO> getDailyInspectionRecordList(
            SearchEquipmentDailyInspectionRecordQuery query) {
        if (CollectionUtil.isNotEmpty(query.getIds())) {
            query.setPageNum(1);
            query.setPageSize(query.getIds().size());
        }
        Page<EquipmentDailyInspectionRecordEntity> page = equipmentDailyInspectionRecordService.page(query.toPage(),
                query.toQueryWrapper());
        List<EquipmentDailyInspectionRecordDTO> records = page.getRecords().stream()
                .map(EquipmentDailyInspectionRecordDTO::new)
                .collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public EquipmentDailyInspectionRecordDTO getDailyInspectionRecordInfo(Long recordId) {
        EquipmentDailyInspectionRecordModel recordModel = equipmentDailyInspectionRecordFactory.loadById(recordId);
        return new EquipmentDailyInspectionRecordDTO(recordModel);
    }

    private String generateSerialNumber() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public EquipmentDataStockEchartDTO getCishu(AreaStatisticsQuery query) {
        return equipmentDailyInspectionRecordService.getCishu(query);
    }
}
