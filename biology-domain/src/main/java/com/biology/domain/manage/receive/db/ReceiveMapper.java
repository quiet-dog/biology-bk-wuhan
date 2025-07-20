package com.biology.domain.manage.receive.db;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biology.domain.manage.receive.dto.ReceiveMaterialsStockDTO;
import com.biology.domain.manage.receive.dto.ReceiveStockDTO;

public interface ReceiveMapper extends BaseMapper<ReceiveEntity> {

        // 获取物料每天的接收数量startTime-endTime,createTime
        @Select("SELECT DATE_FORMAT(create_time, '%m-%d') AS date, SUM(receive_num) AS count FROM manage_receive WHERE materials_id = #{materialsId} AND create_time BETWEEN #{startTime} AND #{endTime} GROUP BY DATE_FORMAT(create_time, '%m-%d')")
        public List<ReceiveStockDTO> getReceiveStock(@Param("materialsId") Long materialsId,
                        @Param("startTime") String startTime,
                        @Param("endTime") String endTime);

        // 获取物料每周每天的接收数量
        @Select("SELECT Count(*) as count,m.name,m.materials_type,m.unit,DATE_FORMAT(r.create_time, '%Y:%m:%d') as data_time,m.name FROM manage_receive r"
                        + " LEFT JOIN manage_materials m on m.materials_id = r.materials_id"
                        + " WHERE m.name = #{name}"
                        + " AND r.create_time >= CURDATE() - INTERVAL 6 DAY AND r.create_time < CURDATE() + INTERVAL 1 DAY"
                        + " GROUP BY DATE_FORMAT(r.create_time, '%Y:%m:%d'),m.materials_type,m.unit"
                        + " ORDER BY m.materials_type")
        public List<ReceiveMaterialsStockDTO> getReceiveStockWeekByName(@Param("name") String name);

        // 获取物料每月每天的接收数量
        @Select("SELECT Count(*) as count,m.name,m.materials_type,m.unit,DATE_FORMAT(r.create_time, '%Y:%m:%d') as data_time,m.name FROM manage_receive r"
                        + " LEFT JOIN manage_materials m on m.materials_id = r.materials_id"
                        + " WHERE m.name = #{name}"
                        + " AND r.create_time >= CURDATE() - INTERVAL 1 MONTH AND r.create_time <= NOW()"
                        + " GROUP BY DATE_FORMAT(r.create_time, '%Y:%m:%d'),m.materials_type,m.unit"
                        + " ORDER BY m.materials_type")
        public List<ReceiveMaterialsStockDTO> getReceiveStockMonthByName(@Param("name") String name);

        // 获取物料每年每月的接收数量
        @Select("SELECT Count(*) as count,m.name,m.materials_type,m.unit,DATE_FORMAT(r.create_time, '%Y:%m') as data_time,m.name FROM manage_receive r"
                        + " LEFT JOIN manage_materials m on m.materials_id = r.materials_id"
                        + " WHERE m.name = #{name}"
                        + " AND YEAR(r.create_time) = YEAR(CURDATE())"
                        + " GROUP BY DATE_FORMAT(r.create_time, '%Y:%m'),m.materials_type,m.unit"
                        + " ORDER BY m.materials_type")
        public List<ReceiveMaterialsStockDTO> getReceiveStockYearByName(@Param("name") String name);

        @Select("SELECT SUM(r.receive_num) as count,m.materials_type as materialsType,m.name FROM manage_receive as r"
                        + " JOIN manage_materials m on m.materials_id = r.materials_id"
                        + " GROUP BY m.name,m.materials_type")
        public List<ReceiveMaterialsStockDTO> getReceiveAllTypeStock();

        // @Select("SELECT SUM(r.receive_num) as count,m.name as materialsType FROM
        // manage_receive as r"
        // + " JOIN manage_materials m on m.materials_id = r.materials_id"
        // + " WHERE m.materials_type = #{name}"
        // + " GROUP BY m.materials_type,m.name")
        @Select("SELECT SUM(stock) as count,name as materialsType FROM manage_materials"
                        + " WHERE materials_type = #{name} AND deleted = 0"
                        + " GROUP BY materials_type,name")
        public List<ReceiveMaterialsStockDTO> getReceiveAllTypeByName(@Param("name") String name);

        @Select("SELECT SUM(r.receive_num) as count,m.name,r.receive_explain from manage_receive as r"
                        + " JOIN manage_materials m on m.materials_id = r.materials_id"
                        + " WHERE m.deleted = 0"
                        + " GROUP BY m.name,r.receive_explain")
        public List<ReceiveMaterialsStockDTO> getReceiveAllReceiveExplain();

        // 获取物料每周每天的接收数量
        @Select("SELECT SUM(receive_num) as count,receive_explain as materials_type,DATE_FORMAT(create_time, '%Y:%m') as data_time FROM manage_receive"
                        + " WHERE create_time >= CURDATE() - INTERVAL 6 DAY AND create_time < CURDATE() + INTERVAL 1 DAY"
                        + " AND materials_id = #{id}"
                        + " GROUP BY DATE_FORMAT(create_time, '%Y:%m'),receive_explain"
                        + " ORDER BY receive_explain")
        public List<ReceiveMaterialsStockDTO> getReceiveStockWeekByNameType(@Param("id") Long id);

        // 获取物料每月每天的接收数量
        @Select("SELECT SUM(receive_num) as count,receive_explain as materials_type,DATE_FORMAT(create_time, '%Y:%m') as data_time FROM manage_receive"
                        + " WHERE materials_id = #{id}"
                        + " AND create_time >= CURDATE() - INTERVAL 1 MONTH AND create_time <= NOW()"
                        + " GROUP BY DATE_FORMAT(create_time, '%Y:%m'),receive_explain"
                        + " ORDER BY receive_explain")
        public List<ReceiveMaterialsStockDTO> getReceiveStockMonthByNameType(@Param("id") Long id);

        // 获取物料每年每月的接收数量
        @Select("SELECT SUM(receive_num) as count,receive_explain as materials_type,DATE_FORMAT(create_time, '%Y-%m') as data_time FROM manage_receive"
                        + " WHERE materials_id = #{id}"
                        + " AND create_time >= DATE_SUB(CURDATE(), INTERVAL 11 MONTH) AND create_time < LAST_DAY(CURDATE()) + INTERVAL 1 DAY"
                        + " GROUP BY DATE_FORMAT(create_time, '%Y-%m'),receive_explain"
                        + " ORDER BY receive_explain")
        public List<ReceiveMaterialsStockDTO> getReceiveStockYearByNameType(@Param("id") Long id);
}
