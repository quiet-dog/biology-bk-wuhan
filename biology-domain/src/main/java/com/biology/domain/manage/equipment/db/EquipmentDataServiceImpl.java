package com.biology.domain.manage.equipment.db;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biology.common.utils.time.DatePickUtil;
import com.biology.domain.manage.equipment.dto.EquipmentDataStockDTO;
import com.biology.domain.manage.equipment.dto.EquipmentDataStockEchartDTO;

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
        echartDTO.setTime(DatePickUtil.getDayNow());
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

        // for (EquipmentDataStockDTO equipmentDataStockDTO : list) {
        // echartDTO.getData().add(equipmentDataStockDTO.getData());
        // echartDTO.getTime().add(equipmentDataStockDTO.getTime());
        // }
        return echartDTO;
    }
}