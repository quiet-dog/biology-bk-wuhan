package com.biology.domain.manage.equipment;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
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
import com.biology.domain.manage.runTime.db.RunTimeEntity;
import com.biology.domain.manage.runTime.db.RunTimeService;

import cn.hutool.core.collection.CollectionUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EquipmentApplicationService {

    private final EquipmentFactory equipmentFactory;
    private final EquipmentService equipmentService;

    private final RunTimeService runTimeService;

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

    public EquipmentDetailDTO getEquipmentInfo(Long equipmentId) {
        EquipmentModel equipmentModel = equipmentFactory.loadById(equipmentId);
        return new EquipmentDetailDTO(equipmentModel);
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

    public String getRunTime(Long equipmentId) {
        QueryWrapper<RunTimeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("equipment_id", equipmentId).eq("is_stop", false).orderByDesc("run_time_id")
                .last("LIMIT 1");
        RunTimeEntity runTimeEntity = runTimeService.getOne(queryWrapper);
        // runTimeEntity.getCreateTime()
        if (runTimeEntity != null && runTimeEntity.getCreateTime() != null) {
            runTimeEntity.setUpdateTime(new Date());

            long diffMillis = runTimeEntity.getUpdateTime().getTime() - runTimeEntity.getCreateTime().getTime();
            if (diffMillis < 0) {
                return "0";
            }

            long hours = TimeUnit.MILLISECONDS.toHours(diffMillis);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis) % 60;
            long seconds = TimeUnit.MILLISECONDS.toSeconds(diffMillis) % 60;
            return String.format("%d小时%d分%d秒", hours, minutes, seconds);
        }
        return "0";
    }

    // public EquipmentDTO getOnlineDevice() {
    // QueryWrapper<EquipmentEntity> queryWrapper = new QueryWrapper<>();
    // long count = equipmentService.count(queryWrapper);

    // }
}
