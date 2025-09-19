package com.biology.domain.manage.equipment.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biology.common.utils.time.DatePickUtil;
import com.biology.domain.manage.equipment.dto.EquipmentDataStockDTO;
import com.biology.domain.manage.equipment.dto.EquipmentDataStockEchartDTO;
import com.biology.domain.manage.equipment.dto.TotalTimeDTO;
import com.biology.domain.manage.threshold.db.ThresholdEntity;

@Service
public class EquipmentDataServiceImpl extends ServiceImpl<EquipmentDataMapper, EquipmentDataEntity>
        implements EquipmentDataService {

    @Override
    public EquipmentDataEntity getById(Long equipmentId) {
        return super.getById(equipmentId);
    }

    @Override
    public EquipmentDataStockEchartDTO getEquipmentDataStockDay(Long threshold) {
        EquipmentDataStockEchartDTO echartDTO = new EquipmentDataStockEchartDTO();
        List<EquipmentDataStockDTO> list = baseMapper.getEquipmentDataStockDay(threshold);
        echartDTO.setData(new ArrayList<>());
        echartDTO.setTime(DatePickUtil.getDayNowHalfHour());
        for (String s : echartDTO.getTime()) {
            Boolean isExit = false;
            for (EquipmentDataStockDTO equipmentDataStockDTO : list) {
                if (s.equals(equipmentDataStockDTO.getTime())) {
                    isExit = true;
                    echartDTO.getData().add(equipmentDataStockDTO.getData());
                    break;
                }
            }
            if (!isExit) {
                echartDTO.getData().add(0.0);
            }
        }

        ThresholdEntity tEntity = new ThresholdEntity().selectById(threshold);
        if (tEntity != null) {
            echartDTO.setUnitName(tEntity.getUnit());
            echartDTO.setSensorName(tEntity.getSensorName());

            EquipmentEntity eEntity = new EquipmentEntity().selectById(tEntity.getEquipmentId());
            if (eEntity != null) {
                echartDTO.setEquipmentName(eEntity.getEquipmentName());
                echartDTO.setEquipmentCode(eEntity.getEquipmentCode());
            }
        }
        // for (EquipmentDataStockDTO equipmentDataStockDTO : list) {
        // echartDTO.getData().add(equipmentDataStockDTO.getData());
        // echartDTO.getTime().add(equipmentDataStockDTO.getTime());
        // }
        return echartDTO;
    }

    public TotalTimeDTO getTotalTime(Long equipmentId) {
        TotalTimeDTO timeDTO = new TotalTimeDTO();
        Integer value = baseMapper.getTotalTime(equipmentId);
        timeDTO.setTotalTime(value);
        return timeDTO;
    }

    public Map<String, Object> getEquipmentDataByEquipmentId(Long threshold, String dayTime) {
        Map<String, Object> map = new HashMap<>();
        List<EquipmentDataStockDTO> list = baseMapper.getHistoryCurentDay(threshold, dayTime);
        // if (list == null) {
        // list = Collections.emptyList();
        // }
        // dayTime是2025-09-18这种格式,判断是否是今天,如果是今天的话,将list的time大于当前time的数据去掉
        List<String> xData = new ArrayList<>();
        List<Double> yData = new ArrayList<>();
        for (EquipmentDataStockDTO item : list) {
            if (item != null) {
                xData.add(item.getTime());
                yData.add(item.getData());
            }
        }

        ThresholdEntity tEntity = new ThresholdEntity().selectById(threshold);
        map.put("xData", xData);
        map.put("yData", yData);
        map.put("unit", tEntity != null ? tEntity.getUnit() : "未知");
        map.put("list", list);

        return map;
    }

}