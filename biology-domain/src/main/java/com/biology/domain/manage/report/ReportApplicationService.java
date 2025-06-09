package com.biology.domain.manage.report;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.receive.db.ReceiveEntity;
import com.biology.domain.manage.receive.dto.ReceiveDTO;
import com.biology.domain.manage.report.command.AddReportCommand;
import com.biology.domain.manage.report.command.UpdateReportCommand;
import com.biology.domain.manage.report.db.ReportEntity;
import com.biology.domain.manage.report.db.ReportService;
import com.biology.domain.manage.report.dto.ReportDTO;
import com.biology.domain.manage.report.dto.StockEchartDTO;
import com.biology.domain.manage.report.dto.StockReportDTO;
import com.biology.domain.manage.report.model.ReportFactory;
import com.biology.domain.manage.report.model.ReportModel;
import com.biology.domain.manage.report.query.ReportQuery;
import com.biology.domain.manage.report.query.StockReportQuery;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportApplicationService {

    private final ReportFactory reportFactory;

    private final ReportService reportService;

    public void create(AddReportCommand command) {
        ReportModel reportModel = reportFactory.create();
        reportModel.loadAddReportCommand(command);
        reportModel.insert();
    }

    public void update(UpdateReportCommand command) {
        ReportModel reportModel = reportFactory.loadById(command.getReportId());
        reportModel.loadUpdateReportCommand(command);
        reportModel.updateById();
    }

    public void delete(Long reportId) {
        ReportModel reportModel = reportFactory.loadById(reportId);
        reportModel.deleteById();
    }

    public void deletes(List<Long> reportIds) {
        reportIds.forEach(reportId1 -> {
            ReportModel reportModel = reportFactory.loadById(reportId1);
            reportModel.deleteById();
        });
    }

    public ReportDTO getById(Long reportId) {
        ReportModel byId = reportFactory.loadById(reportId);
        return new ReportDTO(byId);
    }

    public PageDTO<ReportDTO> getReports(ReportQuery query) {
        Page<ReportEntity> page = reportService.page(query.toPage(), query.toQueryWrapper());
        List<ReportDTO> records = page.getRecords().stream().map(ReportDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public StockEchartDTO stock(StockReportQuery query) {
        List<StockReportDTO> stockReportDTOs = new ArrayList<>();
        StockEchartDTO stockEchartDTO = new StockEchartDTO();
        stockEchartDTO.setSeriesData(new ArrayList<>());
        stockEchartDTO.setXData(new ArrayList<>());
        if ("year".equals(query.getDayType())) {
            stockReportDTOs = reportService.getYearStock(query.getMaterialsId());
        }
        if ("week".equals(query.getDayType())) {
            stockReportDTOs = reportService.getWeekStock(query.getMaterialsId());
        }
        if ("month".equals(query.getDayType())) {
            stockReportDTOs = reportService.getMonthStock(query.getMaterialsId());
        }
        for (StockReportDTO stockReportDTO : stockReportDTOs) {
            stockEchartDTO.getXData().add(stockReportDTO.getName());
            stockEchartDTO.getSeriesData().add(stockReportDTO.getCount());
        }

        return stockEchartDTO;
    }

}
