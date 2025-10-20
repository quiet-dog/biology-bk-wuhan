package com.biology.domain.manage.equipment;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.equipment.command.AddEquipmentCommand;
import com.biology.domain.manage.equipment.command.UpdateEquipmentCommand;
import com.biology.domain.manage.equipment.db.EquipmentEntity;
import com.biology.domain.manage.equipment.db.EquipmentService;
import com.biology.domain.manage.equipment.dto.EquipmentDTO;
import com.biology.domain.manage.equipment.dto.EquipmentDetailDTO;
import com.biology.domain.manage.equipment.model.EquipmentFactory;
import com.biology.domain.manage.equipment.model.EquipmentModel;
import com.biology.domain.manage.equipment.query.SearchEquipmentQuery;

import cn.hutool.core.collection.CollectionUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EquipmentApplicationService {

    private final EquipmentFactory equipmentFactory;
    private final EquipmentService equipmentService;

    public void createEquipment(AddEquipmentCommand command) {
        EquipmentModel equipmentModel = equipmentFactory.create();

        // 查询编号是否存在
        EquipmentEntity equipmentEntity = equipmentService.getOne(new LambdaQueryWrapper<EquipmentEntity>()
                .eq(EquipmentEntity::getEquipmentCode, command.getEquipmentCode()));
        if (equipmentEntity != null) {
            throw new ApiException(ErrorCode.Business.EQUIPMENT_CODE_EXISTS, command.getEquipmentCode());
        }

        equipmentModel.loadAddCommand(command);
        equipmentModel.insert();
    }

    public void updateEquipment(UpdateEquipmentCommand command) {
        EquipmentModel equipmentModel = equipmentFactory.loadById(command.getEquipmentId());
        equipmentModel.loadUpdateCommand(command);
        equipmentModel.update();
    }

    public void deleteEquipments(List<Long> equipmentIds) {
        for (Long equipmentId : equipmentIds) {
            EquipmentModel equipmentModel = equipmentFactory.loadById(equipmentId);
            equipmentModel.deleteById();
        }
    }

    public PageDTO<EquipmentDTO> getEquipmentList(SearchEquipmentQuery query) {
        if (CollectionUtil.isNotEmpty(query.getIds())) {
            query.setPageNum(1);
            query.setPageSize(query.getIds().size());
        }
        Page<EquipmentEntity> page = equipmentService.page(query.toPage(), query.toQueryWrapper());
        List<EquipmentDTO> records = page.getRecords().stream().map(EquipmentDTO::new)
                .collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public EquipmentDTO getEquipmentInfo(Long equipmentId) {
        EquipmentModel equipmentModel = equipmentFactory.loadById(equipmentId);
        return new EquipmentDTO(equipmentModel);
    }

    public PageDTO<EquipmentDetailDTO> getEquipmentDetailList(SearchEquipmentQuery query) {
        if (CollectionUtil.isNotEmpty(query.getIds())) {
            query.setPageNum(1);
            query.setPageSize(query.getIds().size());
        }
        Page<EquipmentEntity> page = equipmentService.page(query.toPage(), query.toQueryWrapper());
        List<EquipmentDetailDTO> records = page.getRecords().stream().map(EquipmentDetailDTO::new)
                .collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public Long getAlarmCount(String dayType) {
        if ("day".equals(dayType)) {
            return equipmentService.getAlarmCountByDay();
        } else if ("month".equals(dayType)) {
            return equipmentService.getAlarmCountByMonth();
        } else if ("year".equals(dayType)) {
            return equipmentService.getAlarmCountByYear();
        }
        return 0L;
    }

    // public EquipmentDTO getOnlineDevice() {
    // QueryWrapper<EquipmentEntity> queryWrapper = new QueryWrapper<>();
    // long count = equipmentService.count(queryWrapper);

    // }
}
