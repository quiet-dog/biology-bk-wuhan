package com.biology.domain.manage.shiJuan.db;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biology.domain.manage.shiJuan.dto.PingFuJieGuoNumDTO;

@Service
public class ResultShiJuanServiceImpl extends ServiceImpl<ResultShiJuanMapper, ResultShiJuanEntity>
                implements ResultShiJuanService {

        public List<PingFuJieGuoNumDTO> getPingGuJieGuoFenXi(String type) {
                return baseMapper.getPingGuJieGuoFenXi(type);
        }

        public List<PingFuJieGuoNumDTO> cePingJieGuoTongJi(Long xlFangAnId) {
                return baseMapper.cePingJieGuoTongJi(xlFangAnId);
        }
}
