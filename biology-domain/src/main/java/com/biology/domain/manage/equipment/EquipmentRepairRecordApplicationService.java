package com.biology.domain.manage.equipment;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.equipment.command.AddEquipmentRepairRecordCommand;
import com.biology.domain.manage.equipment.command.UpdateEquipmentRepairRecordCommand;
import com.biology.domain.manage.equipment.db.EquipmentRepairRecordEntity;
import com.biology.domain.manage.equipment.db.EquipmentRepairRecordService;
import com.biology.domain.manage.equipment.dto.EquipmentDTO;
import com.biology.domain.manage.equipment.dto.EquipmentRepairRecordDTO;
import com.biology.domain.manage.equipment.model.EquipmentFactory;
import com.biology.domain.manage.equipment.model.EquipmentModel;
import com.biology.domain.manage.equipment.model.EquipmentRepairRecordFactory;
import com.biology.domain.manage.equipment.model.EquipmentRepairRecordModel;
import com.biology.domain.manage.equipment.query.EquipmentDataEchartDTO;
import com.biology.domain.manage.equipment.query.SearchEquipmentRepairQuery;
import com.biology.domain.manage.event.dto.EventEchartDTO;

import cn.hutool.core.collection.CollectionUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EquipmentRepairRecordApplicationService {

    private final EquipmentRepairRecordFactory equipmentRepairRecordFactory;
    private final EquipmentRepairRecordService equipmentRepairRecordService;
    private final EquipmentFactory equipmentFactory;

    public void createRepairRecord(AddEquipmentRepairRecordCommand command) {
        EquipmentRepairRecordModel repairModel = equipmentRepairRecordFactory.create();
        repairModel.loadAddCommand(command);
        if (command.getRepairCode() == null || command.getRepairCode().isEmpty()) {
            String randomRepairCode = RandomStringUtils.randomAlphanumeric(8);
            repairModel.setRepairCode(randomRepairCode);
        }
        repairModel.insert();
    }

    public void updateRepairRecord(UpdateEquipmentRepairRecordCommand command) {
        EquipmentRepairRecordModel repairModel = equipmentRepairRecordFactory.loadById(command.getRecordId());
        repairModel.loadUpdateCommand(command);
        repairModel.update();
    }

    public void deleteRepairRecords(List<Long> recordIds) {
        for (Long recordId : recordIds) {
            EquipmentRepairRecordModel repairModel = equipmentRepairRecordFactory.loadById(recordId);
            repairModel.deleteById();
        }
    }

    public PageDTO<EquipmentRepairRecordDTO> getRepairRecordList(SearchEquipmentRepairQuery query) {
        if (CollectionUtil.isNotEmpty(query.getIds())) {
            query.setPageNum(1);
            query.setPageSize(query.getIds().size());
        }

        Page<EquipmentRepairRecordEntity> page = equipmentRepairRecordService.page(query.toPage(),
                query.toQueryWrapper());
        List<EquipmentRepairRecordDTO> records = page.getRecords().stream().map(entity -> {
            EquipmentRepairRecordDTO dto = new EquipmentRepairRecordDTO(entity);
            EquipmentModel equipmentModel = equipmentFactory.loadById(entity.getEquipmentId());
            dto.setEquipment(new EquipmentDTO(equipmentModel));
            return dto;
        }).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public EquipmentRepairRecordDTO getRepairRecordInfo(Long recordId) {
        EquipmentRepairRecordModel repairModel = equipmentRepairRecordFactory.loadById(recordId);
        EquipmentRepairRecordDTO dto = new EquipmentRepairRecordDTO(repairModel);
        EquipmentModel equipmentModel = equipmentFactory.loadById(repairModel.getEquipmentId());
        dto.setEquipment(new EquipmentDTO(equipmentModel));
        if (dto.getEquipment() != null) {
            dto.setEquipmentName(dto.getEquipment().getEquipmentName());
        }
        return dto;
    }

    // 维修记录 按照时间统计 - 年/月/周
    public EventEchartDTO repairRecordByTime(EquipmentDataEchartDTO query) {
        return equipmentRepairRecordService.repairRecordByTime(query);
    }
}
