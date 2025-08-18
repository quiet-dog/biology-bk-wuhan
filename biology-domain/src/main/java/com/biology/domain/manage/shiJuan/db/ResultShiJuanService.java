package com.biology.domain.manage.shiJuan.db;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biology.domain.manage.shiJuan.dto.PingFuJieGuoNumDTO;

public interface ResultShiJuanService extends IService<ResultShiJuanEntity> {

    public List<PingFuJieGuoNumDTO> getPingGuJieGuoFenXi(String type);

    public List<PingFuJieGuoNumDTO> cePingJieGuoTongJi(Long xlFangAnId);
}
