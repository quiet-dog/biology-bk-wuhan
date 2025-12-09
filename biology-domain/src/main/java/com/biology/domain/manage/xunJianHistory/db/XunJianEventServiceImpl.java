package com.biology.domain.manage.xunJianHistory.db;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biology.domain.manage.xunJianHistory.dto.EchartDataDTO;

@Service
public class XunJianEventServiceImpl extends ServiceImpl<XunJianEventMapper, XunJianEventEntity>
                implements XunJianEventService {

        @Override
        public List<EchartDataDTO> getAlarmCountByRecent7Days() {
                return baseMapper.getAlarmCountByRecent7Days();
        }

        @Override
        public List<EchartDataDTO> getAlarmCountByRecent30Days() {
                return baseMapper.getAlarmCountByRecent30Days();
        }

        @Override
        public List<EchartDataDTO> getAlarmCountByRecent1Year() {
                return baseMapper.getAlarmCountByRecent1Year();
        }
}
