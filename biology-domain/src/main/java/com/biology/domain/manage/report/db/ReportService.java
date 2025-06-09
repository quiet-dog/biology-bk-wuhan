package com.biology.domain.manage.report.db;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biology.domain.manage.report.dto.StockReportDTO;

public interface ReportService extends IService<ReportEntity> {

    // public List<StockReportDTO> getWeekStock();

    // public List<StockReportDTO> getMonthStock();

    // public List<StockReportDTO> getYearStock();

    public List<StockReportDTO> getWeekStock(Long id);

    public List<StockReportDTO> getMonthStock(Long id);

    public List<StockReportDTO> getYearStock(Long id);

}
