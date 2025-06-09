package com.biology.domain.manage.report.db;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biology.domain.manage.report.dto.StockReportDTO;

@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, ReportEntity> implements ReportService {

    // @Override
    // public List<StockReportDTO> getWeekStock() {
    // return baseMapper.getWeekStock();
    // }

    // @Override
    // public List<StockReportDTO> getMonthStock() {
    // return baseMapper.getMonthStock();
    // }

    // @Override
    // public List<StockReportDTO> getYearStock() {
    // return baseMapper.getYearStock();

    // }

    @Override
    public List<StockReportDTO> getWeekStock(Long id) {
        return baseMapper.getWeekStock(id);
    }

    @Override
    public List<StockReportDTO> getMonthStock(Long id) {
        return baseMapper.getMonthStock(id);
    }

    @Override
    public List<StockReportDTO> getYearStock(Long id) {
        return baseMapper.getYearStock(id);

    }

}
