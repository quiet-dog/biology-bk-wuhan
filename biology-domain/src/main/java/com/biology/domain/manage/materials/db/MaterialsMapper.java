package com.biology.domain.manage.materials.db;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biology.domain.manage.materials.dto.MhistoryDTO;

public interface MaterialsMapper extends BaseMapper<MaterialsEntity> {

        @Select("SELECT * "
                        + "FROM manage_materials "
                        + "WHERE code = #{code} ")
        MaterialsEntity getMaterialsByCode(String code);

        /**
         * SELECT
         * materials_id,
         * stock,
         * create_time,
         * receive_num AS num
         * FROM manage_receive Where materials_id =1
         * UNION ALL
         * SELECT
         * materials_id ,
         * stock,
         * create_time,
         * report_num AS num
         * FROM manage_report Where materials_id =1
         * UNION ALL
         * SELECT
         * materials_id,
         * stock,
         * create_time,
         * remain_stock AS num
         * FROM manage_warehouse Where materials_id =1;
         * 
         * @param materialsId
         * @return
         */
        @Select("SELECT * FROM ( SELECT "
                        + "    materials_id, "
                        + "    stock, "
                        + "    create_time, "
                        + "    batch,"
                        + "    receive_num AS num, "
                        + "  '出库' AS type "
                        + "FROM manage_receive Where materials_id = #{materialsId} "
                        + "UNION ALL "
                        + "SELECT "
                        + "    materials_id ,"
                        + "    stock,"
                        + "    create_time,"
                        + "    batch,"
                        + "    report_num AS num, "
                        + "  '出库' AS type "
                        + "FROM manage_report Where materials_id = #{materialsId} "
                        + "UNION ALL "
                        + "SELECT "
                        + "    materials_id,"
                        + "    stock AS num,"
                        + "    create_time,"
                        + "    batch,"
                        + "    remain_stock AS stock, "
                        + "  '入库' AS type "
                        + " FROM manage_warehouse Where materials_id = #{materialsId} ) as combined"
                        + " ORDER BY create_time DESC ")
        List<MhistoryDTO> getMaterialsHistory(Long materialsId);

        @Select("SELECT DISTINCT materials_type FROM manage_materials where deleted = 0")
        List<String> getMaterialsTypes();

}
