package com.biology.domain.manage.receive.db;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biology.domain.manage.receive.dto.ReceiveMaterialsAllEchart;
import com.biology.domain.manage.receive.dto.ReceiveMaterialsStockDTO;
import com.biology.domain.manage.receive.dto.ReceiveStockDTO;
import com.biology.domain.manage.receive.dto.ReceiveStockEchart;
import com.biology.domain.manage.receive.dto.ReceiveZhuDTO;
import com.biology.domain.manage.receive.query.ReceiveStockQuery;

public interface ReceiveService extends IService<ReceiveEntity> {

    // 获取物料每天的接收数量startTime-endTime,createTime
    public ReceiveStockEchart getReceiveStock(ReceiveStockQuery receiveStockDTO);

    public List<ReceiveMaterialsStockDTO> getReceiveStockWeekByName(String name);

    public List<ReceiveMaterialsStockDTO> getReceiveStockMonthByName(String name);

    public List<ReceiveMaterialsStockDTO> getReceiveStockYearByName(String name);

    public List<ReceiveMaterialsAllEchart> getReceiveAllTypeStock();

    public List<ReceiveMaterialsStockDTO> getReceiveAllTypeByName(String name);

    public ReceiveZhuDTO getReceiveAllReceiveExplain();

    public List<ReceiveMaterialsStockDTO> getReceiveStockWeekByNameType(Long id);

    public List<ReceiveMaterialsStockDTO> getReceiveStockMonthByNameType(Long id);

    public List<ReceiveMaterialsStockDTO> getReceiveStockYearByNameType(Long id);

}
