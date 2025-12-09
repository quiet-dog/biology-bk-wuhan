package com.biology.domain.manage.xunJianHistory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.xunJian.command.UpdateXunJianCommand;
import com.biology.domain.manage.xunJian.db.XunJianEntity;
import com.biology.domain.manage.xunJian.db.XunJianService;
import com.biology.domain.manage.xunJian.query.XunJianQuery;
import com.biology.domain.manage.xunJianHistory.command.AddXunJianHistoryCommand;
import com.biology.domain.manage.xunJianHistory.command.UpdateXunJianHistoryCommand;
import com.biology.domain.manage.xunJianHistory.db.XunJianEventService;
import com.biology.domain.manage.xunJianHistory.db.XunJianHistoryEntity;
import com.biology.domain.manage.xunJianHistory.db.XunJianHistoryService;
import com.biology.domain.manage.xunJianHistory.dto.EchartDataDTO;
import com.biology.domain.manage.xunJianHistory.dto.XunJianHistoryDTO;
import com.biology.domain.manage.xunJianHistory.model.XunJianHistoryFactory;
import com.biology.domain.manage.xunJianHistory.model.XunJianHistoryModel;
import com.biology.domain.manage.xunJianHistory.query.XunJianHistoryQuery;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class XunJianHistoryApplicationService {
    private final XunJianHistoryFactory xunJianHistoryFactory;

    private final XunJianHistoryService xunJianHistoryService;

    private final XunJianEventService xunJianEventService;

    public void create(AddXunJianHistoryCommand command) {
        XunJianHistoryModel xunJianHistoryModel = xunJianHistoryFactory.create();
        xunJianHistoryModel.loadAddXunJianHistoryCommand(command);
        xunJianHistoryModel.insert();
    }

    public void update(UpdateXunJianHistoryCommand command) {
        XunJianHistoryModel xunJianHistoryModel = xunJianHistoryFactory.loadById(command.getXunJianId());
        xunJianHistoryModel.loadUpdateXunJianHistoryCommand(command);
        xunJianHistoryModel.updateById();
    }

    public void deleteReveives(List<Long> xunJianIds) {
        xunJianIds.forEach(xunJianId -> {
            XunJianHistoryModel xunJianHistoryModel = xunJianHistoryFactory.loadById(xunJianId);
            xunJianHistoryModel.deleteById();
        });
    }

    public PageDTO<XunJianHistoryDTO> getXunJians(XunJianHistoryQuery query) {

        Page<XunJianHistoryEntity> page = xunJianHistoryService.page(query.toPage(), query.toQueryWrapper());
        List<XunJianHistoryDTO> records = page.getRecords().stream().map(XunJianHistoryDTO::new)
                .collect(Collectors.toList());
        records.forEach(item -> {
            item.setTotal(xunJianHistoryService.getTotalByHistoryId(item.getXunJianHistoryId()));
        });
        return new PageDTO<>(records, page.getTotal());
    }

    public XunJianHistoryDTO getXunJianInfo(Long xunJianId) {
        XunJianHistoryEntity byId = xunJianHistoryService.getById(xunJianId);
        return new XunJianHistoryDTO(byId);
    }

    public Object getXunJianHistory(String dayType) {
        Map<String, Object> result = new HashMap<>();
        if (dayType.equals("month")) {
            List<EchartDataDTO> list = xunJianEventService.getAlarmCountByRecent30Days();
            // 是否够30天,不够的日期补齐
            List<String> dateList = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            LocalDate today = LocalDate.now();
            LocalDate startDate = today.minusDays(29); // 包含今天，共30天
            while (!startDate.isAfter(today)) {
                dateList.add(startDate.format(formatter));
                startDate = startDate.plusDays(1);
            }

            List<String> xData = new ArrayList<>();
            List<Integer> yData = new ArrayList<>();
            for (String date : dateList) {
                xData.add(date);
                Boolean isExit = false;
                for (EchartDataDTO item : list) {
                    if (item.getDate().equals(date)) {
                        yData.add(item.getCount());
                        isExit = true;
                        break;
                    }
                }
                if (!isExit) {
                    yData.add(0);
                }
            }
            result.put("xData", xData);
            result.put("yData", yData);
            result.put("data", dateList);
            return result;
        } else if (dayType.equals("year")) {
            List<EchartDataDTO> list = xunJianEventService.getAlarmCountByRecent1Year();
            // 是否够本月+之前的11个月,不够的日期补齐
            List<String> dateList = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
            LocalDate today = LocalDate.now();
            LocalDate startDate = today.minusMonths(11);
            while (!startDate.isAfter(today)) {
                dateList.add(startDate.format(formatter));
                startDate = startDate.plusMonths(1);
            }

            List<String> xData = new ArrayList<>();
            List<Integer> yData = new ArrayList<>();
            for (String date : dateList) {
                Boolean isExit = false;
                xData.add(date);
                for (EchartDataDTO item : list) {
                    if (item.getDate().equals(date)) {

                        yData.add(item.getCount());
                        isExit = true;
                        break;
                    }
                }
                if (!isExit) {
                    yData.add(0);
                }
            }
            result.put("xData", xData);
            result.put("yData", yData);
            return result;
        } else {
            List<EchartDataDTO> list = xunJianEventService.getAlarmCountByRecent7Days();
            List<String> dateList = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate today = LocalDate.now();
            LocalDate startDate = today.minusDays(6);
            while (!startDate.isAfter(today)) {
                dateList.add(startDate.format(formatter));
                startDate = startDate.plusDays(1);
            }
            List<String> xData = new ArrayList<>();
            List<Integer> yData = new ArrayList<>();
            for (String date : dateList) {
                Boolean isExit = false;
                xData.add(date);
                for (EchartDataDTO item : list) {
                    if (item.getDate().equals(date)) {
                        yData.add(item.getCount());
                        isExit = true;
                        break;
                    }
                }
                if (!isExit) {
                    yData.add(0);
                }
            }
            result.put("xData", xData);
            result.put("yData", yData);
            return result;
        }
    }
}
