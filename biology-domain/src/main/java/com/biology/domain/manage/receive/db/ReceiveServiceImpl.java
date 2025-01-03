package com.biology.domain.manage.receive.db;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biology.common.utils.time.DatePickUtil;
import com.biology.domain.manage.receive.dto.ReceiveMaterialsAllEchart;
import com.biology.domain.manage.receive.dto.ReceiveMaterialsStockDTO;
import com.biology.domain.manage.receive.dto.ReceiveStockDTO;
import com.biology.domain.manage.receive.dto.ReceiveStockEchart;
import com.biology.domain.manage.receive.dto.ReceiveZhuDTO;
import com.biology.domain.manage.receive.query.ReceiveStockQuery;

@Service
public class ReceiveServiceImpl extends ServiceImpl<ReceiveMapper, ReceiveEntity> implements ReceiveService {

    public ReceiveStockEchart getReceiveStock(ReceiveStockQuery receiveStockDTO) {
        ReceiveStockEchart result = new ReceiveStockEchart();
        result.setData(new ArrayList<>());
        result.setTime(new ArrayList<>());
        // if (receiveStockDTO.getStartTime().isEmpty() &&
        // receiveStockDTO.getEndTime().isEmpty()) {
        // // 获取物料每天的接收数量startTime-endTime,createTime，最近7天，包括今天
        // LocalDate today = LocalDate.now();
        // LocalDate start = today.minusDays(6);
        // // 格式化为MM-dd
        // receiveStockDTO.setStartTime(start.toString());
        // receiveStockDTO.setEndTime(today.toString());
        // }
        List<ReceiveStockDTO> list = baseMapper.getReceiveStock(receiveStockDTO.getMaterialsId(),
                receiveStockDTO.getStartTime(),
                receiveStockDTO.getEndTime());
        // // 格式化日期 MM-dd,开始时间为startTime，结束时间为endTime，添加到result.time
        // LocalDate start = LocalDate.parse(receiveStockDTO.getStartTime());
        // LocalDate end = LocalDate.parse(receiveStockDTO.getEndTime());
        // while (!start.isAfter(end)) {
        // String str = start.format(DateTimeFormatter.ofPattern("MM-dd"));
        // result.getTime().add(str);
        // start = start.plusDays(1);
        // }

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM-dd");
        // 解析输入时间为 LocalDate
        LocalDate startDate = LocalDate.parse(receiveStockDTO.getStartTime(), inputFormatter);
        LocalDate endDate = LocalDate.parse(receiveStockDTO.getEndTime(), inputFormatter);
        while (!startDate.isAfter(endDate)) {
            result.getTime().add(startDate.format(outputFormatter));
            startDate = startDate.plusDays(1); // 日期加1天
        }

        for (String s : result.getTime()) {
            Boolean isExit = false;
            for (ReceiveStockDTO r : list) {
                if (r.getDate().contains(s)) {
                    isExit = true;
                    result.getData().add(r.getCount());
                    break;
                }
            }
            if (!isExit) {
                result.getData().add(0.0);
            }
        }

        return result;
    }

    @Override
    public List<ReceiveMaterialsStockDTO> getReceiveStockWeekByName(String name) {
        return baseMapper.getReceiveStockWeekByName(name);
    }

    @Override
    public List<ReceiveMaterialsStockDTO> getReceiveStockMonthByName(String name) {
        return baseMapper.getReceiveStockMonthByName(name);
    }

    @Override
    public List<ReceiveMaterialsStockDTO> getReceiveStockYearByName(String name) {
        return baseMapper.getReceiveStockYearByName(name);
    }

    public List<ReceiveMaterialsAllEchart> getReceiveAllTypeStock() {
        List<ReceiveMaterialsAllEchart> rMaterialsAllEchart = new ArrayList<>();
        List<ReceiveMaterialsStockDTO> list = baseMapper.getReceiveAllReceiveExplain();
        if (list != null & list.size() > 0) {
            for (ReceiveMaterialsStockDTO r : list) {
                if (r.getName() == null) {
                    continue;
                }
                Boolean isExit = false;
                for (ReceiveMaterialsAllEchart e : rMaterialsAllEchart) {
                    if (e.getName().equals(r.getName())) {
                        isExit = true;
                        e.getDatas().add(r);
                    }
                }
                if (!isExit) {
                    ReceiveMaterialsAllEchart e = new ReceiveMaterialsAllEchart();
                    e.setName(r.getName());
                    e.setDatas(new ArrayList<>());
                    e.getDatas().add(r);
                    rMaterialsAllEchart.add(e);
                }
            }
        }
        return rMaterialsAllEchart;
    }

    public List<ReceiveMaterialsStockDTO> getReceiveAllTypeByName(String name) {
        return baseMapper.getReceiveAllTypeByName(name);
    }

    public ReceiveZhuDTO getReceiveAllReceiveExplain() {
        ReceiveZhuDTO result = new ReceiveZhuDTO();
        result.setXData(new ArrayList<>());
        List<ReceiveMaterialsStockDTO> list = baseMapper.getReceiveAllReceiveExplain();
        for (ReceiveMaterialsStockDTO r : list) {
            if (r.getName() == null) {
                continue;
            }
            Boolean isExit = false;
            for (String s : result.getXData()) {
                if (s.equals(r.getName())) {
                    isExit = true;
                    result.getXData().add(r.getName());
                    break;
                }
            }
            if (!isExit) {
                result.getXData().add(r.getName());
            }
        }
        List<Double> productionValue = new ArrayList<>();
        List<Double> researchValue = new ArrayList<>();
        List<Double> otherValue = new ArrayList<>();

        for (String s : result.getXData()) {
            Double other = 0.0;
            Double production = 0.0;
            Double research = 0.0;
            for (ReceiveMaterialsStockDTO r : list) {
                if (s.equals(r.getName())) {
                    String receiveExplain = r.getReceiveExplain();
                    if (receiveExplain == null) {
                        other += r.getCount();
                    } else if (receiveExplain.equals("生产领用")) {
                        production = r.getCount();
                    } else if (receiveExplain.equals("研发领用")) {
                        research = r.getCount();
                    } else {
                        other += r.getCount();
                    }
                }
            }
            researchValue.add(research);
            productionValue.add(production);
            otherValue.add(other);
        }

        result.setProductionData(productionValue);
        result.setResearchData(researchValue);
        result.setOtherData(otherValue);
        return result;
    }

    @Override
    public List<ReceiveMaterialsStockDTO> getReceiveStockWeekByNameType(Long id) {
        return baseMapper.getReceiveStockWeekByNameType(id);
    }

    @Override
    public List<ReceiveMaterialsStockDTO> getReceiveStockMonthByNameType(Long id) {
        return baseMapper.getReceiveStockMonthByNameType(id);
    }

    @Override
    public List<ReceiveMaterialsStockDTO> getReceiveStockYearByNameType(Long id) {
        return baseMapper.getReceiveStockYearByNameType(id);
    }
}
