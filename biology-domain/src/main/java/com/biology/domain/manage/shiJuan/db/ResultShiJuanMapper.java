package com.biology.domain.manage.shiJuan.db;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biology.domain.manage.shiJuan.dto.CePingNumDTO;
import com.biology.domain.manage.shiJuan.dto.PingFuJieGuoNumDTO;

public interface ResultShiJuanMapper extends BaseMapper<ResultShiJuanEntity> {

    @Select("select COUNT(*) as num,ce_ping from manage_xl_shijuan_result where ce_ping is not null and deleted = 0 and `type` = #{type} group by ce_ping")
    List<PingFuJieGuoNumDTO> getPingGuJieGuoFenXi(@Param("type") String type);

    @Select("select COUNT(*) as num,ce_ping,type from manage_xl_shijuan_result where ce_ping is not null and deleted = 0 and xl_fang_an_id = #{xlFangAnId} group by ce_ping,type")
    List<PingFuJieGuoNumDTO> cePingJieGuoTongJi(@Param("xlFangAnId") Long xlFangAnId);

    @Select("select COUNT(*) as num,ce_ping,type from manage_xl_shijuan_result where ce_ping is not null and deleted = 0 and xl_fang_an_id in (select xl_fang_an_id from manage_xl_ceping_fangan where name like concat('%', #{fangAnName}, '%')) group by ce_ping,type")
    List<PingFuJieGuoNumDTO> getPingJieGuoTongJiByFangAnName(@Param("fangAnName") String fangAnName);

    @Select("select COUNT(*) as num,ce_ping,type from manage_xl_shijuan_result where ce_ping is not null and deleted = 0 group by ce_ping,type")
    List<PingFuJieGuoNumDTO> getPingJieGuoTongJiByFangAnNameNull();

}
