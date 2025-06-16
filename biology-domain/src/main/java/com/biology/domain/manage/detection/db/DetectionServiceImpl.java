package com.biology.domain.manage.detection.db;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biology.common.utils.time.DatePickUtil;
import com.biology.domain.manage.detection.dto.DareaDTO;
import com.biology.domain.manage.detection.dto.DetectionAreaTypeDTO;
import com.biology.domain.manage.detection.dto.DetectionAreaTypeEchartDTO;
import com.biology.domain.manage.detection.dto.DetectionCountEchartTypeDTO;
import com.biology.domain.manage.detection.dto.DetectionStatisticsDTO;
import com.biology.domain.manage.detection.dto.PowerDTO;
import com.biology.domain.manage.detection.query.DetectionStockQuery;
import com.biology.domain.manage.detection.query.HistoryQuery;
import com.biology.domain.manage.detection.query.PowerQuery;
import com.biology.domain.manage.environment.dto.EnvironmentStatisticsDTO;

@Service
public class DetectionServiceImpl extends ServiceImpl<DetectionMapper, DetectionEntity> implements DetectionService {

    public List<DetectionStatisticsDTO> getStatistics(Long environmentId) {
        return baseMapper.getStatistics(environmentId);
    }

    @Override
    public List<PowerDTO> getPowerStatic(PowerQuery query) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        LocalDate today = LocalDate.now();
        List<PowerDTO> result = new ArrayList<>();

        // 如果存在开始和结束时间,使用传入的时间范围
        if (query.getStartTime() != null && query.getEndTime() != null) {
            List<PowerDTO> data = baseMapper.getPowerStaticByDateRange(query.getDes(), query.getStartTime(),
                    query.getEndTime());
            LocalDate start = query.getStartTime().toLocalDate();
            LocalDate end = query.getEndTime().toLocalDate();

            for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
                PowerDTO powerDTO = new PowerDTO();
                powerDTO.setTime(date.format(formatter));
                boolean isExit = false;
                for (PowerDTO p : data) {
                    if (p.getTime().contains(date.format(formatter))) {
                        powerDTO.setData(p.getData());
                        isExit = true;
                        break;
                    }
                }
                if (!isExit) {
                    powerDTO.setData(0.0);
                }
                result.add(powerDTO);
            }
            return result;
        }

        // 如果没有时间范围,使用原有的逻辑
        if (query.getDayType().equals("week")) {
            List<PowerDTO> data = baseMapper.getPowerStaticWeek(query.getDes());
            LocalDate start = today.minusDays(6);
            for (int i = 0; i < 7; i++) {
                LocalDate date = start.plusDays(i); // 获取前 i 天的日期
                PowerDTO powerDTO = new PowerDTO();
                powerDTO.setTime(date.format(formatter));
                boolean isExit = false;
                for (PowerDTO p : data) {
                    if (p.getTime().contains(date.format(formatter))) {
                        powerDTO.setData(p.getData());
                        isExit = true;
                        break;
                    }
                }
                if (!isExit) {
                    powerDTO.setData(0.0);
                }
                result.add(powerDTO);
            }

            return result;
        } else if (query.getDayType().equals("month")) {
            List<PowerDTO> data = baseMapper.getPowerStaticMonth(query.getDes());
            LocalDate oneMonthAgo = today.minusMonths(1);
            for (LocalDate date = oneMonthAgo; !date.isAfter(today); date = date.plusDays(1)) {
                PowerDTO powerDTO = new PowerDTO();
                powerDTO.setTime(date.format(formatter));
                boolean isExit = false;
                for (PowerDTO p : data) {
                    if (p.getTime().contains(date.format(formatter))) {
                        powerDTO.setData(p.getData());
                        isExit = true;
                        break;
                    }
                }
                if (!isExit) {
                    powerDTO.setData(0.0);
                }
                result.add(powerDTO);
            }
            return result;
        } else if (query.getDayType().equals("year")) {
            List<PowerDTO> data = baseMapper.getPowerStaticYear(query.getDes());
            for (int i = 0; i < 12; i++) {
                PowerDTO powerDTO = new PowerDTO();
                powerDTO.setTime(String.valueOf(i + 1));
                boolean isExit = false;
                for (PowerDTO p : data) {
                    if (p.getTime().contains(String.valueOf(i + 1))) {
                        powerDTO.setData(p.getData());
                        isExit = true;
                        break;
                    }
                }
                if (!isExit) {
                    powerDTO.setData(0.0);
                }
                result.add(powerDTO);
            }
            return result;
        }
        return result;
    }

    @Override
    public List<PowerDTO> getHistoryData(HistoryQuery query) {
        List<PowerDTO> result = baseMapper.getHistoryDataFormat(query.getDescription(), query.getUnitName());
        return result;
    }

    public DetectionAreaTypeEchartDTO getDetectionCountTypeArea(DetectionStockQuery query) {
        DetectionAreaTypeEchartDTO dTo = new DetectionAreaTypeEchartDTO();
        List<DetectionAreaTypeDTO> list = new ArrayList<>();
        if (query.getDescription() != null && !query.getDescription().isEmpty()) {
            list = baseMapper.getDetectionCountTypeAreaAndName(query.getUnitName(), query.getDescription());
        } else {
            list = baseMapper.getDetectionCountTypeArea(query.getUnitName());
        }

        dTo.setTypes(new ArrayList<>());
        dTo.setDatas(new ArrayList<>());
        for (DetectionAreaTypeDTO d : list) {
            dTo.getTypes().add(d.getArea());
            dTo.getDatas().add(d.getCount());
        }
        return dTo;
    }

    public DetectionCountEchartTypeDTO getHistoryPowersByType(PowerQuery query) {
        DetectionCountEchartTypeDTO result = new DetectionCountEchartTypeDTO();
        result.setData(new ArrayList<>());
        result.setTime(DatePickUtil.getDayNow());
        List<PowerDTO> list = new ArrayList<>();
        if (query.getType().equals("电")) {
            list = baseMapper.getHistoryElectricityByType();
        } else if (query.getType().equals("水")) {
            list = baseMapper.getHistoryWaterPowersByType();
        }
        for (String s : result.getTime()) {
            boolean isExit = false;
            for (PowerDTO p : list) {
                if (p.getTime().equals(s)) {
                    result.getData().add(p.getData());
                    isExit = true;
                }
            }
            if (!isExit) {
                result.getData().add(0.0);
            }
        }

        return result;
    }

    public DetectionCountEchartTypeDTO getHistoryPowersByTypeTotal(PowerQuery query) {
        DetectionCountEchartTypeDTO result = new DetectionCountEchartTypeDTO();
        result.setData(new ArrayList<>());

        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        // LocalDate today = LocalDate.now();
        // List<PowerDTO> result = new ArrayList<>();
        if (query.getDayType().equals("week")) {
            result.setTime(DatePickUtil.getWeekNowMMDD());
            List<PowerDTO> data = new ArrayList<>();
            if (query.getType().equals("电")) {
                data = baseMapper.getHistoryElectricityByTypeWeek();
            } else if (query.getType().equals("水")) {
                data = baseMapper.getHistoryWaterByTypeMonth();
            }
            for (String time : result.getTime()) {
                boolean isExit = false;
                for (PowerDTO p : data) {
                    if (p.getTime().contains(time)) {
                        result.getData().add(p.getData());
                        isExit = true;
                        break;
                    }
                }
                if (!isExit) {
                    result.getData().add(0.0);
                }
            }

            // // LocalDate start = today.minusDays(6);
            // for (int i = 0; i < 7; i++) {
            // LocalDate date = start.plusDays(i); // 获取前 i 天的日期
            // result.getTime().add(date.format(formatter));
            // boolean isExit = false;
            // for (PowerDTO p : data) {
            // if (p.getTime().contains(date.format(formatter))) {
            // result.getData().add(p.getData());
            // isExit = true;
            // break;
            // }
            // }
            // if (!isExit) {
            // result.getData().add(0.0);
            // }
            // }
        } else if (query.getDayType().equals("month")) {
            // 获取本月第一天和最后一天
            // LocalDate firstDayOfMonth = today.withDayOfMonth(1); // 本月的第一天
            // LocalDate lastDayOfMonth = today.withDayOfMonth(today.lengthOfMonth()); //
            // 本月的最
            result.setTime(DatePickUtil.getMonthNowMMDD());
            List<PowerDTO> data = new ArrayList<>();
            if (query.getType().equals("电")) {
                data = baseMapper.getHistoryElectricityByTypeMonth();
            } else if (query.getType().equals("水")) {
                data = baseMapper.getHistoryWaterByTypeMonth();
            }
            for (String time : result.getTime()) {
                boolean isExit = false;
                for (PowerDTO p : data) {
                    if (p.getTime().contains(time)) {
                        result.getData().add(p.getData());
                        isExit = true;
                        break;
                    }
                }
                if (!isExit) {
                    result.getData().add(0.0);
                }
            }
            // LocalDate oneMonthAgo = today.minusMonths(1);
            // // 定义日期格式
            // for (LocalDate date = oneMonthAgo; !date.isAfter(today); date =
            // date.plusDays(1)) {
            // result.getTime().add(date.format(formatter));
            // boolean isExit = false;
            // for (PowerDTO p : data) {
            // if (p.getTime().contains(date.format(formatter))) {
            // result.getData().add(p.getData());
            // isExit = true;
            // break;
            // }
            // }
            // if (!isExit) {
            // result.getData().add(0.0);
            // }
            // }

            // LocalDate currentDate = firstDayOfMonth;
        } else if (query.getDayType().equals("year")) {
            result.setTime(DatePickUtil.getYearNowBefore());
            List<PowerDTO> data = new ArrayList<>();
            if (query.getType().equals("电")) {
                data = baseMapper.getHistoryElectricityByTypeYear();
            } else if (query.getType().equals("水")) {
                data = baseMapper.getHistoryWaterByTypeYear();
            }
            for (String time : result.getTime()) {
                boolean isExit = false;
                for (PowerDTO p : data) {
                    if (time.contains(p.getTime())) {
                        result.getData().add(p.getData());
                        isExit = true;
                        break;
                    }
                }
                if (!isExit) {
                    result.getData().add(0.0);
                }
            }

            // for (int i = 0; i < 12; i++) {
            // result.getTime().add(String.valueOf(i + 1));
            // boolean isExit = false;
            // for (PowerDTO p : data) {
            // if (p.getTime().contains(String.valueOf(i + 1))) {
            // result.getData().add(p.getData());
            // isExit = true;
            // break;
            // }
            // }
            // if (!isExit) {
            // result.getData().add(0.0);
            // }
            // }
        }
        return result;
    }

    public DetectionCountEchartTypeDTO getHistoryAreaByTypeTotal(PowerQuery query) {
        DetectionCountEchartTypeDTO result = new DetectionCountEchartTypeDTO();
        result.setData(new ArrayList<>());
        result.setTime(new ArrayList<>());
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        LocalDate today = LocalDate.now();
        // List<PowerDTO> result = new ArrayList<>();
        List<PowerDTO> data = new ArrayList<>();
        if (query.getDayType().equals("week")) {
            if (query.getType().equals("电")) {
                data = baseMapper.getHistoryElectricityByAreaWeek();
            } else if (query.getType().equals("水")) {
                data = baseMapper.getHistoryWaterByAreaWeek();
            }
            // LocalDate start = today.minusDays(6);
            // for (int i = 0; i < 7; i++) {
            // LocalDate date = start.plusDays(i); // 获取前 i 天的日期
            // result.getTime().add(date.format(formatter));
            // boolean isExit = false;
            // for (PowerDTO p : data) {
            // if (p.getTime().contains(date.format(formatter))) {
            // result.getData().add(p.getData());
            // isExit = true;
            // break;
            // }
            // }
            // if (!isExit) {
            // result.getData().add(0.0);

            // }
            // }
        } else if (query.getDayType().equals("month")) {
            // 获取本月第一天和最后一天
            // LocalDate firstDayOfMonth = today.withDayOfMonth(1); // 本月的第一天
            // LocalDate lastDayOfMonth = today.withDayOfMonth(today.lengthOfMonth()); //
            // 本月的最
            if (query.getType().equals("电")) {
                data = baseMapper.getHistoryElectricityByAreaMonth();
            } else if (query.getType().equals("水")) {
                data = baseMapper.getHistoryWaterByAreaMonth();
            }
            // LocalDate currentDate = firstDayOfMonth;
            // while (!currentDate.isAfter(lastDayOfMonth)) {

            // result.getTime().add(currentDate.format(formatter)); // 格式化日期
            // currentDate = currentDate.plusDays(1); // 加一天
            // boolean isExit = false;
            // for (PowerDTO p : data) {
            // if (p.getTime().contains(currentDate.format(formatter))) {
            // result.getData().add(p.getData());
            // isExit = true;
            // break;
            // }
            // }
            // if (!isExit) {
            // result.getData().add(0.0);
            // }
            // }
        } else if (query.getDayType().equals("year")) {
            if (query.getType().equals("电")) {
                data = baseMapper.getHistoryElectricityByAreaYear();
            } else if (query.getType().equals("水")) {
                data = baseMapper.getHistoryWaterByAreaYear();
            }
            // for (int i = 0; i < 12; i++) {
            // result.getTime().add(String.valueOf(i + 1));
            // boolean isExit = false;
            // for (PowerDTO p : data) {
            // if (p.getTime().contains(String.valueOf(i + 1))) {
            // result.getData().add(p.getData());
            // isExit = true;
            // break;
            // }
            // }
            // if (!isExit) {
            // result.getData().add(0.0);
            // }
            // }
        }
        for (PowerDTO p : data) {
            result.getData().add(p.getData());
            result.getTime().add(p.getTime());
        }
        return result;
    }

    public DetectionCountEchartTypeDTO getAareUnitNameHistory(PowerQuery query) {
        DetectionCountEchartTypeDTO result = new DetectionCountEchartTypeDTO();
        result.setData(new ArrayList<>());
        result.setTime(new ArrayList<>());
        if (query.getArea() != null && !query.getArea().isEmpty() && query.getUnitName() != null
                && !query.getUnitName().isEmpty()) {
            if (query.getDayType().equals("week")) {
                List<PowerDTO> list = baseMapper.getAareUnitNameHistoryWeek(query.getUnitName(), query.getArea());
                result.setTime(DatePickUtil.getWeekNowMMDD());
                for (String time : result.getTime()) {
                    boolean isExit = false;
                    for (PowerDTO p : list) {
                        if (p.getTime().contains(time)) {
                            result.getData().add(p.getData());
                            isExit = true;
                            break;
                        }
                    }
                    if (!isExit) {
                        result.getData().add(0.0);
                    }
                }

                // LocalDate start = LocalDate.now().minusDays(6);
                // for (int i = 0; i < 7; i++) {
                // LocalDate date = start.plusDays(i);
                // result.getTime().add(date.toString());
                // boolean isExit = false;
                // for (PowerDTO p : list) {
                // if (p.getTime().contains(date.toString())) {
                // result.getData().add(p.getData());
                // isExit = true;
                // break;
                // }
                // }
                // if (!isExit) {
                // result.getData().add(0.0);
                // }
                // }
            } else if (query.getDayType().equals("month")) {
                result.setTime(DatePickUtil.getMonthNowMMDD());
                List<PowerDTO> list = baseMapper.getAareUnitNameHistoryMonth(query.getUnitName(), query.getArea());
                for (String time : result.getTime()) {
                    boolean isExit = false;
                    for (PowerDTO p : list) {
                        if (p.getTime().contains(time)) {
                            result.getData().add(p.getData());
                            isExit = true;
                            break;
                        }
                    }
                    if (!isExit) {
                        result.getData().add(0.0);
                    }
                }

                // LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
                // LocalDate lastDayOfMonth =
                // LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
                // LocalDate today = LocalDate.now();
                // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
                // List<PowerDTO> list =
                // baseMapper.getAareUnitNameHistoryMonth(query.getUnitName(), query.getArea());
                // LocalDate oneMonthAgo = today.minusMonths(1);
                // // 定义日期格式
                // for (LocalDate date = oneMonthAgo; !date.isAfter(today); date =
                // date.plusDays(1)) {
                // result.getTime().add(date.format(formatter));
                // boolean isExit = false;
                // for (PowerDTO p : list) {
                // if (p.getTime().contains(date.format(formatter))) {
                // result.getData().add(p.getData());
                // isExit = true;
                // break;
                // }
                // }
                // if (!isExit) {
                // result.getData().add(0.0);
                // }
                // }

                // LocalDate currentDate = firstDayOfMonth;
                // while (!currentDate.isAfter(lastDayOfMonth)) {
                // result.getTime().add(currentDate.toString());
                // currentDate = currentDate.plusDays(1);
                // boolean isExit = false;
                // for (PowerDTO p : list) {
                // if (p.getTime().contains(currentDate.toString())) {
                // result.getData().add(p.getData());
                // isExit = true;
                // break;
                // }
                // }
                // if (!isExit) {
                // result.getData().add(0.0);
                // }
                // }

            } else if (query.getDayType().equals("year")) {
                List<PowerDTO> list = baseMapper.getAareUnitNameHistoryYear(query.getUnitName(), query.getArea());
                result.setTime(DatePickUtil.getYearNow());
                for (String s : result.getTime()) {
                    boolean isExit = false;
                    for (PowerDTO p : list) {
                        if (p.getTime().equals(s)) {
                            result.getData().add(p.getData());
                            isExit = true;
                            break;
                        }
                    }
                    if (!isExit) {
                        result.getData().add(0.0);
                    }
                }

                // for (int i = 0; i < 12; i++) {
                // result.getTime().add(String.valueOf(i + 1));
                // boolean isExit = false;
                // for (PowerDTO p : list) {
                // if (p.getTime().contains(String.valueOf(i + 1))) {
                // result.getData().add(p.getData());
                // isExit = true;
                // break;
                // }
                // }
                // if (!isExit) {
                // result.getData().add(0.0);
                // }
                // }

            }
        }
        return result;
    }

    public DetectionCountEchartTypeDTO getHistoryDayByEnvironmentId(PowerQuery query) {
        DetectionCountEchartTypeDTO result = new DetectionCountEchartTypeDTO();
        result.setData(new ArrayList<>());
        result.setTime(DatePickUtil.getDayNow());
        List<PowerDTO> list = baseMapper.getHistoryDayByEnvironmentId(query.getEnvironmentId());
        for (String time : result.getTime()) {
            boolean isExit = false;
            for (PowerDTO p : list) {
                if (p.getTime().contains(time)) {
                    result.getData().add(p.getData());
                    isExit = true;
                    break;
                }
            }
            if (!isExit) {
                result.getData().add(0.0);
            }
        }
        return result;
    }

    public DetectionCountEchartTypeDTO getZuiXinShuJu(PowerQuery query) {
        DetectionCountEchartTypeDTO result = new DetectionCountEchartTypeDTO();
        result.setData(new ArrayList<>());
        result.setTime(new ArrayList<>());
        List<PowerDTO> list = baseMapper.getZuiXinShuJu(query.getArea(), query.getUnitName());
        for (PowerDTO p : list) {
            result.getData().add(p.getData());
            result.getTime().add(p.getTime());
        }
        return result;
    }

    public Double getCurrentMonthPowerUsage(Long environmentId) {
        return baseMapper.getCurrentMonthPowerUsage(environmentId);
    }

    public Double getCurrentMonthWaterUsage(Long environmentId) {
        return baseMapper.getCurrentMonthWaterUsage(environmentId);
    }

    // 获取近7天的数据
    public List<PowerDTO> getElectricityByEnvironmentIdByWeek(Long environmentId) {
        return baseMapper.getElectricityByEnvironmentIdByWeek(environmentId);
    }

    // 获取近30天的数据
    public List<PowerDTO> getElectricityByEnvironmentIdByMonth(Long environmentId) {
        return baseMapper.getElectricityByEnvironmentIdByMonth(environmentId);
    }
    // 获取从当前月往前推1年的每个月的SUM

    public List<PowerDTO> getElectricityByEnvironmentIdByYear(Long environmentId) {
        return baseMapper.getElectricityByEnvironmentIdByYear(environmentId);
    }

    public List<PowerDTO> getWaterByEnvironmentIdByWeek(Long environmentId) {
        return baseMapper.getWaterByEnvironmentIdByWeek(environmentId);
    }

    public List<PowerDTO> getWaterByEnvironmentIdByMonth(Long environmentId) {
        return baseMapper.getWaterByEnvironmentIdByMonth(environmentId);
    }

    public List<PowerDTO> getWaterByEnvironmentIdByYear(Long environmentId) {
        return baseMapper.getWaterByEnvironmentIdByYear(environmentId);
    }

    public List<DareaDTO> getTemperatureDataByAreaAndTimeSlot(String unitName, String beginTime, String endTime) {
        return baseMapper.getTemperatureDataByAreaAndTimeSlot(unitName, beginTime, endTime);
    }
}
