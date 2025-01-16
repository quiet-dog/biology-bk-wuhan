package com.biology.domain.manage.equipment.db;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biology.common.utils.time.DatePickUtil;
import com.biology.domain.manage.equipment.dto.EquipmentDataStockDTO;
import com.biology.domain.manage.equipment.dto.EquipmentDataStockEchartDTO;
import com.biology.domain.manage.event.query.AreaStatisticsQuery;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class EquipmentDailyInspectionRecordServiceImpl
        extends ServiceImpl<EquipmentDailyInspectionRecordMapper, EquipmentDailyInspectionRecordEntity>
        implements EquipmentDailyInspectionRecordService {
    // 在此处实现其他业务逻辑方法
    public EquipmentDataStockEchartDTO getCishu(AreaStatisticsQuery query) {
        EquipmentDataStockEchartDTO eDataStockEchartDTO = new EquipmentDataStockEchartDTO();
        eDataStockEchartDTO.setData(new ArrayList<>());
        List<EquipmentDataStockDTO> list = baseMapper.getCishu(query.getStartTime(), query.getEndTime());
        eDataStockEchartDTO.setTime(DatePickUtil.getStartEndTime(query.getStartTime(), query.getEndTime()));
        for (String s : eDataStockEchartDTO.getTime()) {
            Boolean isflag = false;
            for (EquipmentDataStockDTO e : list) {
                if (s.contains(e.getTime())) {
                    isflag = true;
                    eDataStockEchartDTO.getData().add(e.getData());
                }
            }
            if (!isflag) {
                eDataStockEchartDTO.getData().add(0.0);
            }
        }
        return eDataStockEchartDTO;
    }
}