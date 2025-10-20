package com.biology.domain.manage.report.db;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biology.domain.manage.report.dto.StockReportDTO;

public interface ReportMapper extends BaseMapper<ReportEntity> {

        // @Select("SELECT m.name,SUM(r.report_num) as count FROM manage_report as r"
        // + " JOIN manage_materials m on r.materials_id = m.materials_id"
        // + " WHERE m.name in"
        // + " (SELECT name FROM manage_materials GROUP BY name HAVING COUNT(DISTINCT
        // unit) > 0)"
        // + " AND r.create_time >= CURDATE() - INTERVAL 6 DAY"
        // + " GROUP BY m.name")
        // public List<StockReportDTO> getWeekStock();

        // @Select("SELECT m.name,SUM(r.report_num) as count FROM manage_report as r"
        // + " JOIN manage_materials m on r.materials_id = m.materials_id"
        // + " WHERE m.name in"
        // + " (SELECT name FROM manage_materials GROUP BY name HAVING COUNT(DISTINCT
        // unit) > 0)"
        // + " AND YEAR(r.create_time) = YEAR(CURDATE())"
        // + " GROUP BY m.name")
        // public List<StockReportDTO> getMonthStock();

        // @Select("SELECT m.name,SUM(r.report_num) as count FROM manage_report as r"
        // + " JOIN manage_materials m on r.materials_id = m.materials_id"
        // + " WHERE m.name in"
        // + " (SELECT name FROM manage_materials GROUP BY name HAVING COUNT(DISTINCT
        // unit) > 0)"
        // + " AND YEAR(r.create_time) = YEAR(CURDATE())"
        // + " GROUP BY m.name")
        // public List<StockReportDTO> getYearStock();

        @Select("SELECT r.report_type as name,SUM(r.report_num) as count FROM  manage_report as r"
                        + " JOIN manage_materials m on r.materials_id = m.materials_id"
                        + " WHERE m.materials_id = #{id}"
                        // + " (SELECT name FROM manage_materials GROUP BY name HAVING COUNT(DISTINCT
                        // unit) > 0)"
                        + " AND r.create_time >= CURDATE() - INTERVAL 6 DAY"
                        + " GROUP BY r.report_type")
        public List<StockReportDTO> getWeekStock(Long id);

        @Select("SELECT r.report_type as name,SUM(r.report_num) as count FROM  manage_report as r"
                        + " JOIN manage_materials m on r.materials_id = m.materials_id"
                        + " WHERE m.materials_id = #{id}"
                        // + " AND YEAR(r.create_time) = YEAR(CURDATE())"
                        + " AND r.create_time >= DATE_SUB(CURDATE(), INTERVAL 29 DAY)"
                        + " GROUP BY r.report_type")
        public List<StockReportDTO> getMonthStock(Long id);

        @Select("SELECT r.report_type as name,SUM(r.report_num) as count FROM  manage_report as r"
                        + " JOIN manage_materials m on r.materials_id = m.materials_id"
                        + " WHERE m.materials_id = #{id}"
                        // + " AND YEAR(r.create_time) = YEAR(CURDATE())"
                        + " AND r.create_time >= DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 11 MONTH), '%Y-%m-01')"
                        + " GROUP BY r.report_type")
        public List<StockReportDTO> getYearStock(Long id);

}
