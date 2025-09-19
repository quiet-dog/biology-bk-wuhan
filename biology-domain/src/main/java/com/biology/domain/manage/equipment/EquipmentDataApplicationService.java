package com.biology.domain.manage.equipment;

import org.springframework.stereotype.Service;
import com.biology.domain.manage.equipment.command.AddEquipmentDataCommand;
import com.biology.domain.manage.equipment.command.EquipmentDataHistory;
import com.biology.domain.manage.equipment.command.UpdateEquipmentDataCommand;
import com.biology.domain.manage.equipment.dto.EquipmentDTO;
import com.biology.domain.manage.equipment.dto.EquipmentDataDTO;
import com.biology.domain.manage.equipment.dto.EquipmentDataStockEchartDTO;
import com.biology.domain.manage.equipment.dto.TotalTimeDTO;
import com.biology.domain.manage.equipment.model.EquipmentFactory;
import com.biology.domain.manage.equipment.model.EquipmentDataFactory;
import com.biology.domain.manage.equipment.model.EquipmentDataModel;
import com.biology.domain.manage.equipment.model.EquipmentModel;
import com.biology.domain.manage.equipment.query.SearchEquipmentDataQuery;

import cn.hutool.core.collection.CollectionUtil;

import com.biology.domain.manage.equipment.db.EquipmentDataEntity;
import com.biology.domain.manage.equipment.db.EquipmentDataService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquipmentDataApplicationService {

    private final EquipmentDataFactory equipmentDataFactory;
    private final EquipmentDataService equipmentDataService;
    private final EquipmentFactory equipmentFactory;

    public void createEquipmentData(AddEquipmentDataCommand command) {
        EquipmentDataModel dataModel = equipmentDataFactory.create();
        dataModel.loadAddCommand(command);
        dataModel.insert();
    }

    public EquipmentDataModel addEquipmentData(AddEquipmentDataCommand command) {
        EquipmentDataModel dataModel = equipmentDataFactory.create();
        dataModel.loadAddCommand(command);
        dataModel.insert();
        return dataModel;
    }

    public void updateEquipmentData(UpdateEquipmentDataCommand command) {
        EquipmentDataModel dataModel = equipmentDataFactory.loadById(command.getEquipmentId());
        dataModel.loadUpdateCommand(command);
        dataModel.update();
    }

    public void deleteEquipmentData(List<Long> equipmentIds) {
        for (Long equipmentId : equipmentIds) {
            EquipmentDataModel dataModel = equipmentDataFactory.loadById(equipmentId);
            dataModel.deleteById();
        }
    }

    public PageDTO<EquipmentDataDTO> getEquipmentDataList(SearchEquipmentDataQuery query) {
        if (CollectionUtil.isNotEmpty(query.getIds())) {
            query.setPageNum(1);
            query.setPageSize(query.getIds().size());
        }
        Page<EquipmentDataEntity> page = equipmentDataService.page(query.toPage(), query.toQueryWrapper());
        List<EquipmentDataDTO> records = page.getRecords().stream().map(entity -> {
            EquipmentDataDTO dto = new EquipmentDataDTO(entity);
            // 通过equipmentId查询equipment表获取设备
            EquipmentModel equipmentModel = equipmentFactory.loadById(entity.getEquipmentId());
            dto.setEquipment(new EquipmentDTO(equipmentModel));
            return dto;
        }).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public EquipmentDataDTO getEquipmentDataInfo(Long equipmentId) {
        EquipmentDataModel dataModel = equipmentDataFactory.loadById(equipmentId);
        EquipmentDataDTO dto = new EquipmentDataDTO(dataModel);
        // 通过equipmentId查询equipment表获取设备
        EquipmentModel equipmentModel = equipmentFactory.loadById(dataModel.getEquipmentId());
        dto.setEquipment(new EquipmentDTO(equipmentModel));
        return dto;
    }

    public EquipmentDataStockEchartDTO getEquipmentDataStockDay(Long thresholdId) {
        return equipmentDataService.getEquipmentDataStockDay(thresholdId);
    }

    public TotalTimeDTO getTotalTime(Long equipmentId) {
        return equipmentDataService.getTotalTime(equipmentId);
    }

    public Map<String, Object> getEquipmentDataByEquipmentId(EquipmentDataHistory command) {
        return equipmentDataService.getEquipmentDataByEquipmentId(command.getThresholdId(), command.getDayTime());
    }

}
