package com.biology.domain.manage.threshold;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.environment.db.EnvironmentService;
import com.biology.domain.manage.equipment.db.EquipmentEntity;
import com.biology.domain.manage.threshold.command.AddThresholdCommand;
import com.biology.domain.manage.threshold.command.UpdateThresholdCommand;
import com.biology.domain.manage.threshold.db.ThresholdEntity;
import com.biology.domain.manage.threshold.db.ThresholdService;
import com.biology.domain.manage.threshold.db.ThresholdValueEntity;
import com.biology.domain.manage.threshold.dto.OnlineThresholdEchart;
import com.biology.domain.manage.threshold.dto.ThresholdDTO;
import com.biology.domain.manage.threshold.dto.ThresholdValueDTO;
import com.biology.domain.manage.threshold.model.ThresholdFactory;
import com.biology.domain.manage.threshold.model.ThresholdModel;
import com.biology.domain.manage.threshold.query.ThresholdSearch;
import com.biology.domain.manage.websocket.dto.EquipmentInfoDTO;
import com.biology.domain.manage.websocket.dto.OnlineDTO;
import com.biology.infrastructure.cache.RedisUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThresholdApplicationService {

    private final ThresholdFactory thresholdFactory;

    private final ThresholdService thresholdService;

    private final EnvironmentService environmentService;

    public void createThreshold(AddThresholdCommand command) {
        ThresholdModel thresholdModel = thresholdFactory.create();
        thresholdModel.loadAddThresholdCommand(command);
        thresholdModel.insert();
    }

    public void updateThreshold(UpdateThresholdCommand command) {
        ThresholdModel thresholdModel = thresholdFactory.loadById(command.getThresholdId());
        thresholdModel.loadUpdateThresholdCommand(command);
        thresholdModel.updateById();
    }

    public void deleteThreshold(Long thresholdId) {
        ThresholdModel thresholdModel = thresholdFactory.loadById(thresholdId);
        thresholdModel.deleteById();
    }

    public void deleteThresholds(List<Long> thresholdIds) {
        for (Long thresholdId : thresholdIds) {
            deleteThreshold(thresholdId);
        }
    }

    public ThresholdDTO getThreshold(Long thresholdId) {
        ThresholdModel thresholdModel = thresholdFactory.loadById(thresholdId);
        return new ThresholdDTO(thresholdModel);
    }

    public PageDTO<ThresholdDTO> searchThresholds(ThresholdSearch query) {
        Page<ThresholdEntity> page = thresholdService.page(query.toPage(), query.toQueryWrapper());
        List<ThresholdDTO> records = page.getRecords().stream().map(ThresholdDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public ThresholdValueEntity checkEquipmentInfo(EquipmentInfoDTO equipmentInfo) {
        // QueryWrapper<ThresholdEntity> queryWrapper = new QueryWrapper<>();
        // queryWrapper
        // .eq("equipment_id", equipmentInfo.getEquipmentId())
        // .eq("sensor_name", equipmentInfo.getSensorName());
        ThresholdEntity thresholdEntity = thresholdService.getById(equipmentInfo.getThresholdId());
        if (thresholdEntity == null) {
            return null;
        }
        ThresholdDTO thresholdDTO = new ThresholdDTO(thresholdEntity);
        if (thresholdDTO.getValues() == null || thresholdDTO.getValues().size() == 0) {
            return null;
        }
        for (ThresholdValueEntity thresholdValue : thresholdDTO.getValues()) {
            if (!Double.isNaN(thresholdValue.getMin()) && !Double.isNaN(thresholdValue.getMax())) {
                if (thresholdValue.getMin() < equipmentInfo.getValue()
                        && equipmentInfo.getValue() < thresholdValue.getMax()) {
                    return thresholdValue;
                }
            }
        }
        return null;
    }

    public OnlineThresholdEchart getDeviceStatusEchart() {
        OnlineThresholdEchart result = new OnlineThresholdEchart();
        // long thTotal = thresholdService.count();
        long eqTotal = new EquipmentEntity().selectCount(new QueryWrapper<>());
        // long enTotal = environmentService.count();
        long allTotal = eqTotal;
        result.setTotal(allTotal);
        result.setExceptionCount(0);
        result.setOnlineCount(0);
        result.setOfflineCount(0);
        List<OnlineDTO> cacheList = CacheCenter.onlineCache.getAllCache();
        if (cacheList != null && cacheList.size() > 0) {
            cacheList.forEach(onlineDTO -> {
                if ((onlineDTO.getEquipmentId() != null)) {
                    // 异常的设备
                    if (onlineDTO.getIsException() && onlineDTO.getIsOnline()) {
                        result.setExceptionCount(result.getExceptionCount() + 1);
                    }
                    // 在线的设备
                    if (!onlineDTO.getIsException() && onlineDTO.getIsOnline()) {
                        result.setOnlineCount(result.getOnlineCount() + 1);
                    }
                }
            });
        }
        result.setOfflineCount(allTotal - result.getOnlineCount() - result.getExceptionCount());
        return result;
    }

    // 获取运行时长
    public String getRunTime(Long thresholdId) {
        ThresholdModel thresholdModel = thresholdFactory.loadById(thresholdId);
        thresholdModel.getEquipmentId();
        // 查询这个表的所有equipmentId为这个的threhsoldId
        QueryWrapper<ThresholdEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("equipment_id", thresholdModel.getEquipmentId());
        List<ThresholdEntity> thresholdEntities = new ThresholdEntity().selectList(queryWrapper);
        List<Long> Ids = new ArrayList<>();
        for (ThresholdEntity thresholdEntity : thresholdEntities) {
            Ids.add(thresholdEntity.getThresholdId());
        }
        String result = thresholdService.getRunTime(Ids);
        // 将 xx:xx:xx 转换为 xx小时xx分xx秒
        if (result != null) {
            String[] times = result.split(":");
            String hours = times[0];
            String minutes = times[1];
            String seconds = times[2];
            return hours + "小时" + minutes + "分" + seconds + "秒";
        } else {
            return "0小时0分0秒";
        }
    }
}
