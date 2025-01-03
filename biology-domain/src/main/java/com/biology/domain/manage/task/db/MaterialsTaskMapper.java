package com.biology.domain.manage.task.db;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biology.domain.manage.task.dto.TaskMaterialsDTO;

public interface MaterialsTaskMapper extends BaseMapper<MaterialsTaskEntity> {

        @Select("select SUM(stock) as count,DATE_FORMAT(create_time, '%Y-%m-%d') as time from manage_materials_task"
                        + " WHERE create_time >= CURDATE() - INTERVAL 6 DAY AND create_time < CURDATE() + INTERVAL 1 DAY"
                        + " AND materials_id = #{materialsId}"
                        + " GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d')")
        public List<TaskMaterialsDTO> getStockWeek(@Param("materialsId") Long materialsId);

        @Select("select SUM(stock) as count,DATE_FORMAT(create_time, '%Y-%m-%d') as time from manage_materials_task"
                        + " WHERE create_time >= CURDATE() - INTERVAL 1 MONTH AND create_time <= NOW()"
                        + " AND materials_id = #{materialsId}"
                        + " GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d')")
        public List<TaskMaterialsDTO> getStockMonth(@Param("materialsId") Long materialsId);

        @Select("select SUM(stock) as count,DATE_FORMAT(create_time, '%Y-%m') as time from manage_materials_task"
                        + " WHERE create_time >= DATE_SUB(CURDATE(), INTERVAL 11 MONTH) AND create_time < LAST_DAY(CURDATE()) + INTERVAL 1 DAY"
                        + " AND materials_id = #{materialsId}"
                        + " GROUP BY DATE_FORMAT(create_time, '%Y-%m')")
        public List<TaskMaterialsDTO> getStockYear(@Param("materialsId") Long materialsId);
}
